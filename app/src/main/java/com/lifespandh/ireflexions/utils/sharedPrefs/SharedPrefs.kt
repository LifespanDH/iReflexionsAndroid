package com.lifespandh.ireflexions.utils.sharedPrefs

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.lifespandh.ireflexions.IReflexions

class SharedPrefs {

    private val pm: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(IReflexions.instance)

    var accessToken: String
        get() = pm.getString(ACCESS_TOKEN, "") ?: ""
        set(value) {
            pm.edit().putString(ACCESS_TOKEN, value).apply()
        }

    var refreshToken: String
        get() = pm.getString(REFRESH_TOKEN, "") ?: ""
        set(value) {
            pm.edit().putString(REFRESH_TOKEN, value).apply()
        }
}