package com.lifespandh.ireflexions.home.care

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.dialogs.UserNotLoggedInDialog
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.models.SupportContact
import com.lifespandh.ireflexions.utils.MAX_SUPPORT_CONTACTS_ALLOWED
import com.lifespandh.ireflexions.utils.NEED_HELP_TEXT
import com.lifespandh.ireflexions.utils.launchers.PermissionLauncher
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.network.ID
import com.lifespandh.ireflexions.utils.network.LiveSubject
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import com.lifespandh.ireflexions.utils.phone.getMessageUri
import com.lifespandh.ireflexions.utils.ui.makeGone
import com.lifespandh.ireflexions.utils.ui.makeVisible
import com.lifespandh.ireflexions.utils.ui.toast
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.care_center_text_crisis_tab.*
import kotlinx.android.synthetic.main.care_center_therapist_tab.*
import kotlinx.android.synthetic.main.fragment_care_center.*


class CareCenterFragment : BaseFragment(), PermissionLauncher.OnPermissionResult,
    SupportContactAdapter.OnSupportContactClicked {

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val supportContactAdapter by lazy { SupportContactAdapter(mutableListOf(), this) }
    private val permissionLauncher =  PermissionLauncher(this, this)

    private val compositeDisposable by lazy { CompositeDisposable() }

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
        contactsLoader.makeVisible()
        homeViewModel.getSupportContacts()
        setViews()
        setListeners()
        setObservers()
        setSubscribers()
    }

    private fun setViews() {
        support_contacts_recyclerView.apply {
            adapter = supportContactAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setListeners() {
        addContactCardView.setOnClickListener {
            if (!canAddSupportContact()) {
                toast("Cannot add more than $MAX_SUPPORT_CONTACTS_ALLOWED contacts")
                return@setOnClickListener
            }
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
            contactsLoader.makeGone()
            supportContactAdapter.setList(it)
        }

        homeViewModel.supportContactDeletedLiveData.observeFreshly(viewLifecycleOwner) {
            toast("Contact Deleted")
        }
    }

    private fun setSubscribers() {
        val supportContactDisposable = LiveSubject.supportContactAdded.observeOn(Schedulers.io()).subscribe({
            supportContactAdapter.addToList(it)
        }, {
            logE("error $it")
        })

        compositeDisposable.add(supportContactDisposable)
    }

    private fun showDialog(title: String, message: String) {
        UserNotLoggedInDialog.newInstance(
            title, message
        ).show(requireActivity().supportFragmentManager, null)
    }

    private fun openMessageAppWithPhoneNumber(phoneNumber: String, messageText:String = "") {
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

    private fun showMoreActionsMenuPopup(view: View, supportContact: SupportContact) {
        val popup = PopupMenu(requireContext(), view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.support_contact_menu, popup.menu)
        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
            if (it?.itemId == R.id.action_edit_support_contact) {
                if (requireActivity().checkSelfPermission(android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    val action = CareCenterFragmentDirections.actionCareCenterFragmentToEditSupportContactFragment(supportContact, true)
                    findNavController().navigate(action)
                } else {
                    permissionLauncher.launch(android.Manifest.permission.READ_CONTACTS, {
                        val action = CareCenterFragmentDirections.actionCareCenterFragmentToEditSupportContactFragment(supportContact, true)
                        findNavController().navigate(action)
                    })
                }
                return@OnMenuItemClickListener true
            }
            if (it?.itemId == R.id.action_delete_support_contact) {
                // add api call to delete contact
                val requestBody = createJsonRequestBody(ID to supportContact.id)
                homeViewModel.deleteSupportContact(requestBody)
                supportContactAdapter.deleteContact(supportContact)
                return@OnMenuItemClickListener true
            }
            return@OnMenuItemClickListener false
        })
        popup.show()
    }

    private fun canAddSupportContact(): Boolean {
        return supportContactAdapter.itemCount < MAX_SUPPORT_CONTACTS_ALLOWED
    }

    companion object {
        fun newInstance() = CareCenterFragment()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
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
        openMessageAppWithPhoneNumber(phoneNumber, messageText = NEED_HELP_TEXT)
    }

    override fun moreActionsClicked(view: View, supportContact: SupportContact) {
        showMoreActionsMenuPopup(view, supportContact)
    }

}