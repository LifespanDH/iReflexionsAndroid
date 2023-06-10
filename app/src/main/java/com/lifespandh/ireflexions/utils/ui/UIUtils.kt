package com.lifespandh.ireflexions.utils.ui

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.requireContext(), message, length).show()
}

fun TextView.trimString(): String {
    return this.text.toString().trim()
}

fun areFieldsEmpty(vararg textViews: TextView): Boolean {
    var isEmpty: Boolean
    for (tv in textViews) {
        isEmpty = tv.trimString().isEmpty()
        if (isEmpty)
            return true
    }
    return false
}

fun areStringsEmpty(vararg strings: String): Boolean {
    var isEmpty: Boolean
    for (string in strings) {
        isEmpty = string.trim().isEmpty()
        if (isEmpty)
            return true
    }
    return false
}