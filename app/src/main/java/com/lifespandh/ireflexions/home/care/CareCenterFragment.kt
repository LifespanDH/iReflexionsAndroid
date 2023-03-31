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
import com.lifespandh.ireflexions.utils.permissions.PermissionLauncher
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
            if (sharedPrefs.isLoggedIn) {
                if (requireActivity().checkSelfPermission(android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    findNavController().navigate(R.id.editSupportContactFragment)
                } else {
                    PermissionLauncher(this, object : PermissionLauncher.OnPermissionResult {
                        override fun onPermissionGranted() {

                        }

                        override fun onPermissionDenied() {

                        }
                    }).launch(android.Manifest.permission.READ_CONTACTS)
                }
            } else {
                showDialog(
                    requireContext().getString(R.string.member_ship_level_no_subscription_dialog_title),
                    requireContext().getString(R.string.explore_without_an_account_Program_text)
                )
            }

            mindfulness_tab.setOnClickListener {
                val bundle = Bundle()
                bundle.putBoolean("is_mindfulness",true)
                findNavController().navigate(R.id.action_careCenterFragment_to_careCenterExerciseFragment,bundle)
            }

            guided_meditation_tab.setOnClickListener {
                val bundle = Bundle()
                bundle.putBoolean("is_mindfulness",false)
                findNavController().navigate(R.id.action_careCenterFragment_to_careCenterExerciseFragment,bundle)
            }
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