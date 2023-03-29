package com.lifespandh.ireflexions.home.care

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.dialogs.UserNotLoggedInDialog
import kotlinx.android.synthetic.main.fragment_care_center.*

class CareCenterFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_care_center, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setListeners()
    }

    private fun setListeners() {
        addContactCardView.setOnClickListener {
            showDialog(
                requireContext().getString(R.string.member_ship_level_no_subscription_dialog_title),
                requireContext().getString(R.string.explore_without_an_account_Program_text)
            )
        }
    }

    private fun showDialog(title: String, message: String) {
        UserNotLoggedInDialog.newInstance(
            title, message
        ).show(requireActivity().supportFragmentManager, null)
    }

    companion object {
        fun newInstance() = CareCenterFragment()
    }
}