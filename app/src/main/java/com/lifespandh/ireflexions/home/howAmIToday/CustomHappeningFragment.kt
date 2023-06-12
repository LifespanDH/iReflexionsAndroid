package com.lifespandh.ireflexions.home.howAmIToday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseDialogFragment
import kotlinx.android.synthetic.main.custom_happening.btn_save
import kotlinx.android.synthetic.main.custom_happening.custom_name_editText
import kotlinx.android.synthetic.main.fragment_text_crisis_lines.close_dialog_textView

class CustomHappeningFragment: BaseDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.custom_happening, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        setListeners()
    }

    private fun setListeners(){
        close_dialog_textView.setOnClickListener {
            dismiss()
        }

        btn_save.setOnClickListener {
            //custom_name_editText.text
        }
    }
}