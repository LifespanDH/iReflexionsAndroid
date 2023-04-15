package com.lifespandh.ireflexions.auth

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseActivity
import com.lifespandh.ireflexions.home.HomeActivity
import com.lifespandh.ireflexions.home.HomeFragment
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logD
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.network.*
import com.lifespandh.ireflexions.utils.ui.toast
import com.lifespandh.ireflexions.utils.ui.trimString
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var signUpRequest: BeginSignInRequest
    private val LOGIN_REQUEST_CODE = 12
    private val authViewModel by viewModels<AuthViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {
        sharedPrefs.isLoggedIn = false
        initializeAuth()
        setListeners()
        setObservers()
    }
    private fun initializeAuth() {
        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.your_web_client_id))
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()

        signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.your_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }

    private fun setListeners() {
        signInButton.setOnClickListener {
            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this) { result ->
                    try {
                        startIntentSenderForResult(
                            result.pendingIntent.intentSender, LOGIN_REQUEST_CODE,
                            null, 0, 0, 0, null
                        )
                    } catch (e: IntentSender.SendIntentException) {
                        logE("Couldn't start One Tap UI: ${e.localizedMessage}")
                    }
                }
                .addOnFailureListener(this) { e ->
                    oneTapClient.beginSignIn(signUpRequest)
                        .addOnSuccessListener(this) { result ->
                            try {
                                startIntentSenderForResult(
                                    result.pendingIntent.intentSender, LOGIN_REQUEST_CODE,
                                    null, 0, 0, 0
                                )
                            } catch (e: IntentSender.SendIntentException) {
                                logE("called $e")
                            }
                        }
                        .addOnFailureListener(this) { e ->
                            logE("called $e")
                        }
                }
        }

        signupbutton.setOnClickListener {
            startActivity(RegistrationActivity.newInstance(this))
        }

        skip_button.setOnClickListener {
            startActivity(HomeActivity.newInstance(this))
        }

        loginbutton.setOnClickListener {
            val email = emailtext.trimString()
            val password = passwordtext.trimString()
            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                toast("Please enter details correctly")
                return@setOnClickListener
            }
            val requestBody = createJsonRequestBody(EMAIL to email, PASSWORD to password)
            authViewModel.loginCustomUser(requestBody)
        }
    }

    private fun setObservers() {
        authViewModel.tokenLiveData.observeFreshly(this) {
            tokenViewModel.saveToken(it.token)
            tokenViewModel.saveRefreshToken(it.refresh)
            logE("called tokebn it ${it.refresh}")
        }

        tokenViewModel.token.observeFreshly(this) {
            if (it != null) {
                sharedPrefs.isLoggedIn = true
                startActivity(HomeActivity.newInstance(this))
            }
        }

        authViewModel.errorLiveData.observeFreshly(this) {
            toast(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            12 -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.googleIdToken
                    val username = credential.id
                    val password = credential.password
                    val requestBody = createJsonRequestBody(OAUTH_KEY to idToken)
                    authViewModel.loginUser(requestBody)
                } catch (e: ApiException) {
                }
            }
        }
    }

    companion object {

        fun newInstance(context: Context) = Intent(context, LoginActivity::class.java)
    }
}





