package com.lifespandh.ireflexions.utils.sharedPrefs

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.lifespandh.ireflexions.IReflexions

class SharedPrefs {

    private val pm: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(IReflexions.instance)

//   var latestSupplierId: String
//        get() = pm.getString(LATEST_SUPPLIER_ID, "") ?: ""
//        set(value) {
//            pm.edit().putString(LATEST_SUPPLIER_ID, value).apply()
//        }
}