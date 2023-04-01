package com.lifespandh.ireflexions

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.logs.logV
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IReflexions: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        try {
            // Add these lines to add the AWSCognitoAuthPlugin and AWSS3StoragePlugin plugins
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSS3StoragePlugin())
            Amplify.configure(applicationContext)
            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }
    }

    companion object {
        lateinit var instance: IReflexions
            private set
    }

}