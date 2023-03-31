package com.lifespandh.ireflexions

import android.app.Application
import com.amplifyframework.AmplifyException
import com.amplifyframework.core.Amplify
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.logs.logV
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IReflexions: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        try {
            Amplify.configure(applicationContext)
            logV( "Initialized Amplify", "MyAmplifyApp")
        } catch (error: AmplifyException) {
            logE("Could not initialize Amplify", "MyAmplifyApp")
        }
    }

    companion object {
        lateinit var instance: IReflexions
            private set
    }

}