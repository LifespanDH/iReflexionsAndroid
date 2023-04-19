package com.lifespandh.ireflexions.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.auth.LoginActivity
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.dialogs.UserNotLoggedInDialog
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.ui.makeInvisible
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setUpViews()
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        tokenViewModel.token.observeFreshly(viewLifecycleOwner) {
            token = it
        }
    }

    private fun setListeners() {
//        exercise.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_exerciseFragment)
//        }

        logout.setOnClickListener {
            tokenViewModel.deleteToken()
            tokenViewModel.deleteRefreshToken()
            startActivity(LoginActivity.newInstance(requireContext()))
        }

        resourceLibrary.setOnClickListener {
            // Open resource library here
        }

        courses.setOnClickListener {
            logE("called click ${sharedPrefs.isLoggedIn}")
//            if (!sharedPrefs.isLoggedIn) {
//                showDialog(
//                    requireContext().getString(R.string.member_ship_level_no_subscription_dialog_title),
//                    requireContext().getString(R.string.explore_without_an_account_Program_text)
//                )
//            } else {
                findNavController().navigate(R.id.action_homeFragment_to_courseListFragment)
//            }
        }

        careCenter.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_careCenterFragment)
        }

        community.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_communityFragment)
        }

        howAmI.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_howAmINoEntryFragment)
        }

        home_screen_login_button.setOnClickListener{
            startActivity(LoginActivity.newInstance(requireContext()))
        }
    }

    private fun showDialog(title: String, message: String) {
        UserNotLoggedInDialog.newInstance(
            title, message
        ).show(requireActivity().supportFragmentManager, null)
    }

    private fun setUpViews() {
        home_screen_login_button.isVisible = !sharedPrefs.isLoggedIn
        logout.isVisible = sharedPrefs.isLoggedIn
    }

    companion object {

        fun newInstance() = HomeFragment()
    }
}