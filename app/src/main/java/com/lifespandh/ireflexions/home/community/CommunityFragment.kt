package com.lifespandh.ireflexions.home.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebStorage
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.lifespandh.ireflexions.BuildConfig
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.auth.TokenViewModel
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.utils.HP_ACCESS_KEY
import com.lifespandh.ireflexions.utils.HP_EMAIL
import com.lifespandh.ireflexions.utils.HP_FIRST_NAME
import com.lifespandh.ireflexions.utils.HP_LAST_NAME
import com.lifespandh.ireflexions.utils.HP_UUID
import com.lifespandh.ireflexions.utils.jwt.getEmailFromJWT
import com.lifespandh.ireflexions.utils.jwt.getNameFromJWT
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import com.lifespandh.ireflexions.utils.ui.makeGone
import kotlinx.android.synthetic.main.fragment_community.*

class CommunityFragment : BaseFragment() {

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }

    private var token: String = ""
    private var userEmail: String? = null
    private var userName: String? = null

    private var userExists = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val requestBody = createJsonRequestBody(HP_ACCESS_KEY to BuildConfig.HEY_PEERS_ACCESS_KEY)
        homeViewModel.heyPeersAuthenticate(requestBody)
        setupWebView()
        setObservers()
    }

    private fun setupWebView() {
        // Clear all the Application Cache, Web SQL Database and the HTML5 Web Storage
        WebStorage.getInstance().deleteAllData();

        // Clear all the cookies
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();


//        communityWebView.apply {
//            settings.javaScriptEnabled = true
//            loadUrl("https://www.heypeers.com/")
//        }
    }

    private fun setObservers() {
        tokenViewModel.token.observeFreshly(viewLifecycleOwner) {
            userEmail = it?.getEmailFromJWT()
            userName = it?.getNameFromJWT()

            logE("called $userEmail $userName $it")
        }

        homeViewModel.heyPeersTokenLiveData.observeFreshly(viewLifecycleOwner) {
            token = it
            val requestBody = createJsonRequestBody(HP_EMAIL to userEmail, HP_FIRST_NAME to userName, HP_LAST_NAME to "")
            homeViewModel.heyPeersCreateUser(BuildConfig.HEY_PEERS_ORG_ID, "Bearer $it", requestBody)
        }

        homeViewModel.heyPeersUUIDLiveData.observeFreshly(viewLifecycleOwner) {
            if (it != null) {
                loader.makeGone()
                communityWebView.apply {
                    clearCache(true);
                    clearFormData();
                    clearHistory();
                    clearSslPreferences();
                    isVisible = true
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    loadUrl(it.second)
                }
            }
            userExists = it == null
            val requestBody = createJsonRequestBody(HP_UUID to it?.first)
            homeViewModel.saveHPUUID(requestBody)
        }

        homeViewModel.hPUUIDLiveData.observeFreshly(viewLifecycleOwner) {
            if (it.isNullOrEmpty().not() && userExists)
                homeViewModel.heyPeersGenerateOTLLink(BuildConfig.HEY_PEERS_ORG_ID, it, "Bearer $token")
        }

        homeViewModel.heyPeersOTLLinkLiveData.observeFreshly(viewLifecycleOwner) {
            loader.makeGone()

            communityWebView.apply {
                clearCache(true);
                clearFormData();
                clearHistory();
                clearSslPreferences();                isVisible = true
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl(it)
            }
        }

        homeViewModel.errorLiveData.observeFreshly(viewLifecycleOwner) {
            logE("called error $it")
        }
    }

    companion object {

        fun newInstance() = CommunityFragment()
    }
}