package com.lifespandh.ireflexions.utils

import android.os.Handler
import android.os.Looper


fun runInHandler(action: () -> Unit) {
    Handler(Looper.getMainLooper()).post {
        action()
    }
}