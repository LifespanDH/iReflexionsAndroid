package com.lifespandh.ireflexions.home.care

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.DialogFragment
import com.lifespandh.ireflexions.R

class EditSupportContactFragment : DialogFragment(), PopupMenu.OnMenuItemClickListener {
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            dialog
        } ?: throw IllegalStateException("Activity cannot be null.")
    }

}