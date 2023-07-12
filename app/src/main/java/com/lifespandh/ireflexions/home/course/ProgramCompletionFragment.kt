package com.lifespandh.ireflexions.home.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseDialogFragment
import kotlinx.android.synthetic.main.fragment_program_completion.newProgram
import kotlinx.android.synthetic.main.fragment_program_completion.takeBreak

class ProgramCompletionFragment: BaseDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_program_completion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setListeners()
    }

    private fun setListeners() {
        newProgram.setOnClickListener {

        }

        takeBreak.setOnClickListener {
            dismiss()
        }
    }
}