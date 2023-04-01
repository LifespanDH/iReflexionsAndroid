package com.lifespandh.ireflexions.utils.sharedPrefs

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.lifespandh.ireflexions.IReflexions

class SharedPrefs {

    private val pm: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(IReflexions.instance)

    var isLoggedIn: Boolean
        get() = pm.getBoolean(IS_LOGGED_IN, false) ?: false
        set(value) {
            pm.edit().putBoolean(IS_LOGGED_IN, value).apply()
        }

    var membershipLevel: Int
        get() = pm.getInt(MEMBERSHIP_LEVEL, 1)
        set(value) {
            pm.edit().putInt(MEMBERSHIP_LEVEL, value).apply()
        }
}