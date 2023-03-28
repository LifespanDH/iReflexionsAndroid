package com.lifespandh.ireflexions.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.lifespandh.ireflexions.R
import kotlinx.android.synthetic.main.user_not_logged_in_dialog.view.*

class UserNotLoggedInDialog: DialogFragment() {

    companion object {
        const val TAG = "ExploreWithoutAnAccountDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_BODY = "KEY_BODY"

        fun newInstance(title: String, bodyText: String) = UserNotLoggedInDialog().apply {
            arguments = Bundle().apply {
                putString(KEY_TITLE, title)
                putString(KEY_BODY, bodyText)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_not_logged_in_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupView(view: View) {
        view.tvTitle.text = arguments?.getString(KEY_TITLE)
        view.tvBody.text = arguments?.getString(KEY_BODY)
    }

    private fun setupClickListeners(view: View) {
        view.btnOk.setOnClickListener {
            dismiss()
        }
    }

}