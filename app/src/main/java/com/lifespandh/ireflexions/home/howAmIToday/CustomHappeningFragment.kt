package com.lifespandh.ireflexions.home.howAmIToday

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseDialogFragment
import kotlinx.android.synthetic.main.custom_happening.btn_save
import kotlinx.android.synthetic.main.custom_happening.custom_name_editText
import kotlinx.android.synthetic.main.fragment_text_crisis_lines.close_dialog_textView

class CustomHappeningFragment: BaseDialogFragment() {

    private lateinit var view: View
    private lateinit var closeDialogImage : ImageView
    private lateinit var editText: EditText
    private lateinit var saveButton: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.custom_happening, null)
            builder.setView(view)

            val dialog = builder.create()
            dialog.setCanceledOnTouchOutside(false)

            this.view = view
            init()

            dialog
        } ?: throw IllegalStateException("Activity cannot be null.")
    }

    private fun init(){
        initViews()
        setListeners()
    }

    private fun initViews(){
        closeDialogImage = view.findViewById(R.id.img_close)
        editText = view.findViewById(R.id.custom_name_editText)
        saveButton = view.findViewById(R.id.btn_save)
    }
    private fun setListeners(){
        closeDialogImage.setOnClickListener {
            dismiss()
        }

        saveButton.setOnClickListener {
            //custom_name_editText.text
        }
    }
}