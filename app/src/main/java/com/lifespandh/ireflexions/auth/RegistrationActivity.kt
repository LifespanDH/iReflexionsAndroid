package com.lifespandh.ireflexions.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseActivity
import com.lifespandh.ireflexions.models.User
import com.lifespandh.ireflexions.utils.date.DATE_FORMAT
import com.lifespandh.ireflexions.utils.date.getDateAfterDays
import com.lifespandh.ireflexions.utils.date.getDateInFormat
import com.lifespandh.ireflexions.utils.date.toDate
import com.lifespandh.ireflexions.utils.dialogs.DialogUtils
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.ui.areFieldsEmpty
import com.lifespandh.ireflexions.utils.ui.areStringsEmpty
import com.lifespandh.ireflexions.utils.ui.toast
import com.lifespandh.ireflexions.utils.ui.trimString
import kotlinx.android.synthetic.main.activity_registration.*
import java.util.*

class RegistrationActivity : BaseActivity() {

    private val authViewModel by viewModels<AuthViewModel> { viewModelFactory }
    private val dialogUtils = DialogUtils()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        init()
    }

    private fun init() {
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        dateOfBirth.setOnClickListener {
            MaterialDialog(this).show {
                datePicker(
                    currentDate = Calendar.getInstance(),
                    maxDate = getDateAfterDays(1)
                ) { _, date ->
                    this@RegistrationActivity.dateOfBirth.setText(getDateInFormat(date.timeInMillis))
                }
            }
        }

        registerButton.setOnClickListener {
            val areFieldsEmpty = areFieldsEmpty(name, email, phone, region, dateOfBirth, password)
            if (areFieldsEmpty) {
                toast("Please enter all details")
                return@setOnClickListener
            }

            val name = name.trimString()
            val email = email.trimString()
            val phone = phone.trimString()
            val region = region.trimString()
            val dob = dateOfBirth.trimString().getDateInFormat(DATE_FORMAT)
            val password = password.trimString()

            val user = dob?.let { it1 -> User(name, email, phone, it1, region, password) }
            if (user != null) {
                authViewModel.registerUser(user)
            }
        }
    }

    private fun setObservers() {
        authViewModel.userRegisteredLiveData.observeFreshly(this) {
            dialogUtils.showMessageDialog(this, "Success", "User Registered", {
                finish()
            })
        }

        authViewModel.errorLiveData.observeFreshly(this) {
            toast(it)
        }
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, RegistrationActivity::class.java)
    }
}