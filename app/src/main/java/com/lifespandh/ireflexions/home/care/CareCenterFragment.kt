package com.lifespandh.ireflexions.home.care

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.dialogs.UserNotLoggedInDialog
import com.lifespandh.ireflexions.utils.launchers.PermissionLauncher
import com.lifespandh.ireflexions.utils.logs.logE
import kotlinx.android.synthetic.main.fragment_care_center.*

class CareCenterFragment : BaseFragment(), PermissionLauncher.OnPermissionResult {

    private val permissionLauncher =  PermissionLauncher(this, this)

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
            if (sharedPrefs.isLoggedIn) {
                if (requireActivity().checkSelfPermission(android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    val action = CareCenterFragmentDirections.actionCareCenterFragmentToEditSupportContactFragment(null, false)
                    findNavController().navigate(action)
                } else {
                    permissionLauncher.launch(android.Manifest.permission.READ_CONTACTS)
                }
            } else {
                showDialog(
                    requireContext().getString(R.string.member_ship_level_no_subscription_dialog_title),
                    requireContext().getString(R.string.explore_without_an_account_Program_text)
                )
            }
        }

        mindfulness_tab.setOnClickListener {
            val action = CareCenterFragmentDirections.actionCareCenterFragmentToCareCenterExerciseFragment(true)
            findNavController().navigate(action)
        }

        guided_meditation_tab.setOnClickListener {
            val action = CareCenterFragmentDirections.actionCareCenterFragmentToCareCenterExerciseFragment(false)
            findNavController().navigate(action)
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

    override fun onPermissionGranted() {

    }

    override fun onPermissionDenied() {

    }
}