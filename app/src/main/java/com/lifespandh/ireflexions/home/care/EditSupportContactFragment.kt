package com.lifespandh.ireflexions.home.care

import android.app.Dialog
import android.graphics.Bitmap
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseDialogFragment
import com.lifespandh.ireflexions.dialogs.UserNotLoggedInDialog
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.models.MembershipLevel
import com.lifespandh.ireflexions.models.SupportContact
import com.lifespandh.ireflexions.utils.dialogs.DialogUtils
import com.lifespandh.ireflexions.utils.image.getBitmapFromUriPath
import com.lifespandh.ireflexions.utils.image.serializeToJson
import com.lifespandh.ireflexions.utils.launchers.ContactPickerLauncher
import com.lifespandh.ireflexions.utils.launchers.ImageCaptureLauncher
import com.lifespandh.ireflexions.utils.launchers.ImagePickerLauncher
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.logs.logV
import com.lifespandh.ireflexions.utils.network.ID
import com.lifespandh.ireflexions.utils.network.LiveSubject
import com.lifespandh.ireflexions.utils.network.UploadFileStatus
import com.lifespandh.ireflexions.utils.network.aws.S3UploadWorker
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import com.lifespandh.ireflexions.utils.ui.makeGone
import com.lifespandh.ireflexions.utils.ui.makeVisible
import com.lifespandh.ireflexions.utils.ui.toast
import com.lifespandh.ireflexions.utils.ui.trimString
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_edit_support_contact.imageUploadProgressBar
import java.io.File
import java.util.Locale


class EditSupportContactFragment : BaseDialogFragment(), PopupMenu.OnMenuItemClickListener, ContactPickerLauncher.OnContactPicked,
    ImagePickerLauncher.OnImagePicked, ImageCaptureLauncher.OnImageCaptured {

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val args: EditSupportContactFragmentArgs by navArgs()
    private val contactPickerLauncher = ContactPickerLauncher(this, this)
    private val imagePickerLauncher = ImagePickerLauncher(this, this)
    private val imageCaptureLauncher = ImageCaptureLauncher(this, this)

    private var dialogUtils = DialogUtils()
    private var supportContact: SupportContact? = null
    private var inEditMode = false
    private var imageUrl: String? = null

    private val compositeDisposable by lazy { CompositeDisposable() }

    private lateinit var view_: View
    private lateinit var nameEditText : EditText
    private lateinit var phoneEditText : EditText
    private lateinit var deleteButton : Button
    private lateinit var saveButton : Button
    private lateinit var closeDialog : TextView
    private lateinit var contactIconImage : ImageView
    private lateinit var imageUploadProgressBar: ProgressBar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_edit_support_contact, null)
            builder.setView(view)

            val dialog = builder.create()
            dialog.setCanceledOnTouchOutside(false)

            this.view_ = view
            init()

            dialog
        } ?: throw IllegalStateException("Activity cannot be null.")
    }
    private fun init() {
        getValuesFromArgument()
        initViews()
        setupViews()
        setListeners()
        setObservers()
        setSubscribers()
    }

    private fun initViews(){
        nameEditText = view_.findViewById<EditText>(R.id.name_editText)
        phoneEditText = view_.findViewById<EditText>(R.id.phone_editText)
        deleteButton = view_.findViewById(R.id.delete_button)
        saveButton = view_.findViewById(R.id.save_button)
        closeDialog = view_.findViewById(R.id.close_dialog_textView)
        contactIconImage = view_.findViewById(R.id.contact_icon_imageView)
        imageUploadProgressBar = view_.findViewById(R.id.imageUploadProgressBar)
    }

    private fun setupViews() {
        nameEditText.setText(supportContact?.name ?: "")
        phoneEditText.setText(supportContact?.phoneNumber ?: "")
        supportContact?.image?.let { setContactImage(compressedBitmap = null, url = it) }

        deleteButton.isVisible = inEditMode
    }

    private fun getValuesFromArgument() {
        arguments?.let { _ ->
            supportContact = args.supportContact
            inEditMode = args.inEditMode
        }
    }

    private fun setListeners(){

        closeDialog.setOnClickListener {
            dismiss()
        }

        saveButton.setOnClickListener {
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
                // Remove the below line after fixing URL from AWS
                if (imageUrl?.isEmpty() == true) {
                    toast("Please wait for image to be uploaded")
                    return@setOnClickListener
                }
                imageUrl = "https://www.testlink.com"
                val name = supportContact?.name ?: nameEditText.trimString()
                val phoneNumber = supportContact?.phoneNumber ?: phoneEditText.trimString()
                val image = supportContact?.image ?: imageUrl

                /**
                 * Need to add check for image too after AWS code
                 */
                if (name.isNullOrEmpty() || phoneNumber.isNullOrEmpty()) {
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

        deleteButton.setOnClickListener {
            showDeleteContactConfirmationDialog {
                val requestBody = createJsonRequestBody(ID to supportContact?.id)
                homeViewModel.deleteSupportContact(requestBody)
            }
        }

        contactIconImage.setOnClickListener { view ->
            showPhotoActionMenuPopup(view)
        }

        nameEditText.setOnTouchListener { view, motionEvent ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (motionEvent.rawX >= nameEditText.right - nameEditText.compoundDrawables
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
            dialog?.dismiss()
            LiveSubject.supportContactAdded.onNext(it)
            toast("Contact Added")
        }

        homeViewModel.supportContactEditedLiveData.observeFreshly(this) {
            dialog?.dismiss()
            toast("Contact edited")
        }

        homeViewModel.supportContactDeletedLiveData.observeFreshly(this) {
            toast("Contact deleted")
            dialog?.dismiss()
        }

        homeViewModel.errorLiveData.observeFreshly(this) {
            toast(it)
            dialog?.dismiss()
        }
    }

    private fun setSubscribers() {
        val fileUploadDisposable = LiveSubject.FILE_UPLOAD_FILE.subscribe({
            when(it) {
                is UploadFileStatus.Complete -> {
                    imageUploadProgressBar.makeGone()
                    logV("Image uploaded successfully ${it.s3Url}")
                    imageUrl = it.s3Url
                }
                is UploadFileStatus.Error -> {
                    imageUploadProgressBar.makeGone()
                    toast("There was some issue in uploading the image")
                    // Remove image from the imageView here, and return to default
                }
                is UploadFileStatus.FileStatus -> {
                    logE("Current progress is $it")
                    // Change progress of the progress bar here
                }
                is UploadFileStatus.Start -> {
                    logV("Image upload started")
                }
            }
        }, {
            logE("Image upload error $it")
        })
        compositeDisposable.add(fileUploadDisposable)
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
            .into(view_.findViewById<ImageView>(R.id.contact_icon_imageView))
    }

    private fun uploadImageToAWS(compressedBitmap: Bitmap?) {
        imageUploadProgressBar.makeVisible()
        imageUrl = ""
        val bitmapString = serializeToJson(compressedBitmap)
        val builder = Data.Builder()
        builder.putString(S3UploadWorker.IMAGE_BITMAP_STRING, bitmapString)
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(S3UploadWorker::class.java)
            .setInputData(builder.build())
            .build()
        WorkManager.getInstance(requireContext()).enqueue(oneTimeWorkRequest)
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

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        item?.let {
            return when (it.itemId) {
                R.id.action_take_photo -> {
                    imageCaptureLauncher.launch(requireContext())
                    true
                }
                R.id.action_select_photo -> {
                    imagePickerLauncher.launch(IMAGE_PICKER_INPUT, requireContext())
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
        if (bitmap != null) {
            contactIconImage.setImageBitmap(bitmap)
        }
        nameEditText.setText(name)
        phoneEditText.setText(number)
    }

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

}