package com.lifespandh.ireflexions.home.care

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseDialogFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.home.exercise.ExerciseFragment
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.android.synthetic.main.fragment_edit_support_contact.*
import kotlinx.coroutines.launch
import java.io.*
import java.util.*

class EditSupportContactFragment : BaseDialogFragment(), PopupMenu.OnMenuItemClickListener {

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_support_contact, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init(){
        setListeners()
        setObservers()
    }

    companion object {
        fun newInstance() = EditSupportContactFragment()

    }

    private fun setListeners(){

        close_dialog_textView.setOnClickListener {
            dismiss()
        }

        save_button.setOnClickListener {

        }

    }

    private fun setObservers(){
        homeViewModel.supportContactAddedLiveData.observeFreshly(this) {
            if(it)
                homeViewModel.addSupportContact()
        }

    }


    private val pickContactResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val contactData = result.data!!.data!!
                val projection = arrayOf<String>(
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                )
                val cursor: Cursor = requireActivity().contentResolver.query(
                    contactData, projection,
                    null, null, null
                )!!
                cursor.moveToFirst()

                val numberColumnIndex =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val number = cursor.getString(numberColumnIndex)

                val nameColumnIndex =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val name = cursor.getString(nameColumnIndex)

                val contact =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
                val id = cursor.getString(contact)

                setContactImage(id)
                viewModel.updateWithContact(name, number)
            }
        }
    private fun setContactImage(id: String) {
        val imageView = contact_icon_imageView
        val photoUri = Uri.withAppendedPath(
            ContactsContract.Contacts.CONTENT_URI,
            id
        )
        try {
            val photoStream: InputStream =
                ContactsContract.Contacts.openContactPhotoInputStream(
                    requireActivity().contentResolver,
                    photoUri
                )
            val buf = BufferedInputStream(photoStream)
            val bitmap: Bitmap = BitmapFactory.decodeStream(buf)
            imageView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace();

        }
    }

    private val takePictureAction =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            if (it != null) {
                try {
                    val actualImage = FileUtil.from(
                        requireContext(),
                        ImageUtils.getImageUri(requireContext(), it)!!
                    )
                    compressImage(actualImage)
                } catch (e: IOException) {
                    showError("Failed to read picture data!")
                    e.printStackTrace()
                }
            } else {
                showToast("A photo is not captured.")
            }
        }

    private val selectPictureAction2 =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                try {
                    val actualImage = FileUtil.from(requireContext(), it)
                    compressImage(actualImage)
                    Log.d(TAG, "selectPictureAction2 -> ${it.path}")

                } catch (e: IOException) {
                    showError("Failed to read picture data!")
                    e.printStackTrace()
                }

            }
        }
    private fun showError(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun compressImage(actualImage: File) {
        actualImage.let { imageFile ->
            lifecycleScope.launch {

                val compressedImage = Compressor.compress(requireContext(), imageFile) {
                    resolution(100, 100)
                    quality(80)
                    format(Bitmap.CompressFormat.PNG)
                    size(30000) // 30 KB
                }

                val bitmapImage = BitmapFactory.decodeFile(compressedImage.path)
                //viewModel.updatePhotoUri(viewModel.encodeToBase64(bitmapImage)!!)

                val imageView = contact_icon_imageView
                val bitmap = BitmapFactory.decodeStream(imageFile.inputStream())

                Glide.with(this@EditSupportContactFragment)
                    .asBitmap()
                    .load(bitmap)
                    .centerCrop()
                    .placeholder(R.drawable.add_person_icon)
                    .error(R.drawable.error_exclamation)
                    .fallback(R.drawable.info_icon)
                    .into(imageView)
            }
        }
    }

    fun showDeleteContactConfirmationDialog(
        okClickAction: () -> Unit
    ) {

        dialogUtils.showPopupOkCancelDialog(
            requireContext(),
            getString(R.string.action_delete_contact),
            getString(
                R.string.text_delete_contact,
                contactName,
                PhoneNumberUtils.formatNumber(phoneNumber, Locale.US.country)
            ),
            okClickAction
        )
    }

    private fun showDialog(title: String, message: String) {
        ExploreWithoutAnAccountDialog.newInstance(title, message)
            .show(requireActivity().supportFragmentManager, TAG)
    }

    private fun showPhotoActionMenuPopup(view: View) {
        val popup = PopupMenu(requireContext(), view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.take_picture_menu, popup.menu)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        item?.let {
            return when (it.itemId) {
                R.id.action_take_photo -> {
                    val imageFile =
                        getImageFile()
                    if (!imageFile.exists())
                        imageFile.createNewFile()
                    val extUrl = FileProvider.getUriForFile(
                        requireContext(),
                        "${requireContext().packageName}.fileprovider",
                        imageFile
                    )
                    takePictureAction.launch()
                    true
                }
                R.id.action_select_photo -> {
                    selectPictureAction2.launch("image/*")
                    true
                }
                else -> {
                    false
                }
            }
        }
        return false
    }

    private fun getImageFile(): File {
        val dir = requireContext().cacheDir
        return File("${dir.absoluteFile}/$photoCacheFileName")
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun getCachePhotoFileName(): String {
        photoCacheFileName = "img${Date().time}"
        return photoCacheFileName
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.getContentResolver(),
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

}