package com.lifespandh.ireflexions.home.care

import android.graphics.Bitmap
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.*
import android.view.View.OnTouchListener
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseDialogFragment
import com.lifespandh.ireflexions.dialogs.UserNotLoggedInDialog
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.models.MembershipLevel
import com.lifespandh.ireflexions.models.SupportContact
import com.lifespandh.ireflexions.utils.dialogs.DialogUtils
import com.lifespandh.ireflexions.utils.image.getBitmapFromUriPath
import com.lifespandh.ireflexions.utils.image.getImageUri
import com.lifespandh.ireflexions.utils.launchers.ContactPickerLauncher
import com.lifespandh.ireflexions.utils.launchers.ImageCaptureLauncher
import com.lifespandh.ireflexions.utils.launchers.ImagePickerLauncher
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.network.ID
import com.lifespandh.ireflexions.utils.network.aws.S3UploadService
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import com.lifespandh.ireflexions.utils.ui.toast
import com.lifespandh.ireflexions.utils.ui.trimString
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.fragment_edit_support_contact.*
import java.io.File
import java.util.*


class EditSupportContactFragment : BaseDialogFragment(), PopupMenu.OnMenuItemClickListener, ContactPickerLauncher.OnContactPicked {

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val args: EditSupportContactFragmentArgs by navArgs()
    private val contactPickerLauncher =
        ContactPickerLauncher(this, this)


    private var dialogUtils = DialogUtils()
    private var supportContact: SupportContact? = null
    private var inEditMode = false

    private var imageUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // check this container - > null
        return inflater.inflate(R.layout.fragment_edit_support_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        getValuesFromArgument()
        setupViews()
        setListeners()
        setObservers()
    }

    private fun setupViews() {
        name_editText.setText(supportContact?.name ?: "")
        phone_editText.setText(supportContact?.phoneNumber ?: "")
        supportContact?.image?.let { setContactImage(compressedBitmap = null, url = it) }

        delete_button.isVisible = inEditMode
    }

    private fun getValuesFromArgument() {
        arguments?.let { _ ->
            supportContact = args.supportContact
            inEditMode = args.inEditMode
        }
    }

    private fun setListeners(){

        close_dialog_textView.setOnClickListener {
            dismiss()
        }

        save_button.setOnClickListener {
            if (!sharedPrefs.isLoggedIn) {
                showDialog(
                    requireContext().getString(R.string.explore_without_an_account_dialog_title),
                    requireContext().getString(R.string.explore_without_an_account_dialog_body_text_entry)
                )
            } else if (sharedPrefs.membershipLevel == MembershipLevel.Basic.level) {
                showDialog(
                    requireContext().getString(R.string.member_ship_level_no_subscription_dialog_title),
                    requireContext().getString(R.string.member_ship_level_no_subscription_dialog_body_text_entry)
                )
            } else {
                val name = supportContact?.name ?: name_editText.trimString()
                val phoneNumber = supportContact?.phoneNumber ?: phone_editText.trimString()
                val image = supportContact?.image ?: imageUrl

                if (name.isNullOrEmpty() || phoneNumber.isNullOrEmpty() || image.isNullOrEmpty()) {
                    toast("Incomplete Information")
                }
                if (supportContact == null) {
                    val supportContact = SupportContact(name = name, phoneNumber = phoneNumber, image = image)
                    homeViewModel.addSupportContact(supportContact)
                } else {
                    val supportContact = SupportContact(id = supportContact!!.id, name = name, phoneNumber = phoneNumber, image = image)
                    homeViewModel.editSupportContact(supportContact)
                }
            }
        }

        delete_button.setOnClickListener {
            showDeleteContactConfirmationDialog {
                val requestBody = createJsonRequestBody(ID to supportContact?.id)
                homeViewModel.deleteSupportContact(requestBody)
            }
        }

        contact_icon_imageView.setOnClickListener {
            if (inEditMode) {
                view?.let { it1 -> showPhotoActionMenuPopup(it1) }
            }
        }

        name_editText.setOnTouchListener { view, motionEvent ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (motionEvent.rawX >= name_editText.right - name_editText.compoundDrawables
                        .get(DRAWABLE_RIGHT).bounds.width()
                ) {
                    // your action here
                    contactPickerLauncher.launch(requireContext())
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun setObservers(){
        homeViewModel.supportContactAddedLiveData.observeFreshly(this) {
            toast("Contact added")
        }

        homeViewModel.supportContactEditedLiveData.observeFreshly(this) {
            toast("Contact edited")
        }

        homeViewModel.supportContactDeletedLiveData.observeFreshly(this) {
            toast("Contact deleted")
        }
    }

    private fun showDeleteContactConfirmationDialog(
        okClickAction: () -> Unit
    ) {
        dialogUtils.showPopupOkCancelDialog(
            requireContext(),
            getString(R.string.action_delete_contact),
            getString(
                R.string.text_delete_contact,
                supportContact?.name,
                PhoneNumberUtils.formatNumber(supportContact?.phoneNumber, Locale.US.country)
            ),
            okClickAction
        )
    }

    private fun showDialog(title: String, message: String) {
        UserNotLoggedInDialog.newInstance(title, message)
            .show(parentFragmentManager, TAG)
    }

    private fun showPhotoActionMenuPopup(view: View) {
        val popup = PopupMenu(requireContext(), view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.take_picture_menu, popup.menu)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }

    private fun setContactImage(compressedBitmap: Bitmap?, url: String? = null) {
        Glide.with(this@EditSupportContactFragment)
            .asBitmap()
            .load(compressedBitmap ?: url)
            .centerCrop()
            .placeholder(R.drawable.add_person_icon)
            .error(R.drawable.error_exclamation)
            .fallback(R.drawable.info_icon)
            .into(contact_icon_imageView)
    }

    private fun uploadImageToAWS(compressedBitmap: Bitmap?) {
        val uri = compressedBitmap?.let { getImageUri(requireContext(), it) }
        val intent = uri?.let { S3UploadService.newInstance(it) }
        S3UploadService.enqueueWork(requireContext(), intent)
    }

    companion object {
        private val TAG = EditSupportContactFragment::class.simpleName

        private val IMAGE_PICKER_INPUT = "image/*"

        enum class OPERATION_TYPE {
            EDIT,
            CREATE
        }

        fun newInstance() = EditSupportContactFragment()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        item?.let {
            return when (it.itemId) {
                R.id.action_take_photo -> {
                    ImageCaptureLauncher(
                        this,
                        requireContext(),
                        object : ImageCaptureLauncher.OnImageCaptured {
                            override fun onImageCaptured(
                                actualImage: File?,
                                compressedBitmap: Bitmap?
                            ) {
                                uploadImageToAWS(compressedBitmap)
                                setContactImage(compressedBitmap)
                            }

                            override fun onImageNotCaptured(exception: Exception?) {
                                toast("Image could not be captured")
                            }
                        })
                    true
                }
                R.id.action_select_photo -> {
                    ImagePickerLauncher(
                        this,
                        requireContext(),
                        object : ImagePickerLauncher.OnImagePicked {
                            override fun onImagePickResult(
                                originalImage: File,
                                compressedBitmap: Bitmap?
                            ) {
                                uploadImageToAWS(compressedBitmap)
                                setContactImage(compressedBitmap)
                            }

                            override fun onPickError(exception: Exception) {
                                toast("Error")
                            }
                        }).launch(IMAGE_PICKER_INPUT)
                    true
                }
                else -> {
                    false
                }
            }
        }
        return false
    }

    override fun contactPicked(name: String?, number: String?, image: String?) {
        val bitmap = image?.let { it1 -> getBitmapFromUriPath(it1, requireContext()) }
        contact_icon_imageView.setImageBitmap(bitmap)

        name_editText.setText(name)
        phone_editText.setText(number)
    }
}