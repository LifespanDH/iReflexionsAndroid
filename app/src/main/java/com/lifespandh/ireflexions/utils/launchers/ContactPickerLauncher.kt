package com.lifespandh.ireflexions.utils.launchers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.provider.ContactsContract
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class ContactPickerLauncher(
    fragment: Fragment,
    context: Context,
    listener: OnContactPicked
) {

    private val pickContactResult =
        fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val contactData = result.data!!.data!!
                val projection = arrayOf<String>(
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                )
                val cursor: Cursor? = context.contentResolver.query(
                    contactData, projection,
                    null, null, null
                )
                cursor?.moveToFirst()

                val numberColumnIndex =
                    cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val number = numberColumnIndex?.let { cursor?.getString(it) }

                val nameColumnIndex =
                    cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val name = nameColumnIndex?.let { cursor?.getString(it) }

                val contact =
                    cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
                val id = contact?.let { cursor?.getString(it) }
                listener.contactPicked(name, number, id)
//                setContactImage(id)
//                viewModel.updateWithContact(name, number)
            }
        }

    fun launch() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        intent.setDataAndType(null, ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE)
        pickContactResult.launch(intent)
    }

    interface OnContactPicked {
        fun contactPicked(name: String?, number: String?, image: String?)
    }

}