package com.lifespandh.ireflexions.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.dialogs.UserNotLoggedInDialog
import com.lifespandh.ireflexions.home.exercise.ExerciseFragment
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.runBlocking

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
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        tokenViewModel.token.observeFreshly(viewLifecycleOwner) {
            token = it
        }
    }

    private fun setListeners() {
        exercise.setOnClickListener {
            setupFragment(ExerciseFragment.newInstance())
        }

        courses.setOnClickListener {
            if (token.isNullOrEmpty()) {
                showDialog(
                    requireContext().getString(R.string.member_ship_level_no_subscription_dialog_title),
                    requireContext().getString(R.string.explore_without_an_account_Program_text)
                )
            } else {

            }
        }
    }

    private fun setupFragment(fragment: Fragment = HomeFragment.newInstance()) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.frameContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showDialog(title: String, message: String) {
//        ExploreWithoutAnAccountDialog.newInstance(title, message)
//            .show(requireActivity().supportFragmentManager, TAG)
        UserNotLoggedInDialog.newInstance(
            title, message
        ).show(requireActivity().supportFragmentManager, null)
    }

    companion object {

        fun newInstance() = HomeFragment()
    }
}