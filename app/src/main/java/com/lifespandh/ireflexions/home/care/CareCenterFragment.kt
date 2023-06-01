package com.lifespandh.ireflexions.home.care

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.dialogs.UserNotLoggedInDialog
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.utils.launchers.PermissionLauncher
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.phone.getMessageUri
import kotlinx.android.synthetic.main.care_center_text_crisis_tab.*
import kotlinx.android.synthetic.main.care_center_therapist_tab.*
import kotlinx.android.synthetic.main.fragment_care_center.*


class CareCenterFragment : BaseFragment(), PermissionLauncher.OnPermissionResult,
    SupportContactAdapter.OnSupportContactClicked {

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val supportContactAdapter by lazy { SupportContactAdapter(listOf(), this) }
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
        homeViewModel.getSupportContacts()
        setViews()
        setListeners()
        setObservers()
    }

    private fun setViews() {
        support_contacts_recyclerView.apply {
            adapter = supportContactAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setListeners() {
        addContactCardView.setOnClickListener {
            if (sharedPrefs.isLoggedIn) {
                if (requireActivity().checkSelfPermission(android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    val action = CareCenterFragmentDirections.actionCareCenterFragmentToEditSupportContactFragment(null, true)
                    findNavController().navigate(action)
                } else {
                    permissionLauncher.launch(android.Manifest.permission.READ_CONTACTS)
                }
            } else {
                showDialog(
                    requireContext().getString(R.string.member_ship_level_no_subscription_dialog_title),
                    requireContext().getString(R.string.explore_without_an_account_care_center_text)
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

        text_crisis_button.setOnClickListener {
            if (sharedPrefs.isLoggedIn) {
                findNavController().navigate(R.id.action_careCenterFragment_to_textCrisisLinesFragment)
            }
            else {
                showDialog(
                    requireContext().getString(R.string.member_ship_level_no_subscription_dialog_title),
                    requireContext().getString(R.string.explore_without_an_account_care_center_text_crisis_lines)
                )

            }
        }

        call_therapist_button.setOnClickListener {
            if (sharedPrefs.isLoggedIn) {
                val phone = "+18773425152"
                openPhoneApp(phone)
            }
            else {
                showDialog(
                    requireContext().getString(R.string.member_ship_level_no_subscription_dialog_title),
                    requireContext().getString(R.string.explore_without_an_account_care_center_call_therapist)
                )
            }
        }
    }

    private fun setObservers() {
        homeViewModel.supportContactsLiveData.observeFreshly(viewLifecycleOwner) {
            logE("called here $it")
            supportContactAdapter.setList(it)
        }
    }

    private fun showDialog(title: String, message: String) {
        UserNotLoggedInDialog.newInstance(
            title, message
        ).show(requireActivity().supportFragmentManager, null)
    }

    private fun openMessageAppWithPhoneNumber(phoneNumber: String, messageText:String = "``") {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = getMessageUri(phoneNumber)
            putExtra("sms_body", messageText)
        }
        startActivity(intent)
    }

    private fun openPhoneApp(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        startActivity(intent)
    }

    companion object {
        fun newInstance() = CareCenterFragment()
    }

    override fun onPermissionGranted() {
        addContactCardView.callOnClick()
    }

    override fun onPermissionDenied() {

    }

    override fun callContactClicked(phoneNumber: String) {
        openPhoneApp(phoneNumber)
    }

    override fun textContactClicked(phoneNumber: String) {
        openMessageAppWithPhoneNumber(phoneNumber)
    }

    override fun moreActionsClicked() {
//        TODO("Not yet implemented")
    }

}