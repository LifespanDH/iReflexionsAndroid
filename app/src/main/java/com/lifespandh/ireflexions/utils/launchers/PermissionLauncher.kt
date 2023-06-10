package com.lifespandh.ireflexions.utils.launchers

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class PermissionLauncher(
    fragment: Fragment,
    listener: OnPermissionResult
) {

    private var onPermissionGranted: (() -> Unit)? = null
    private var onPermissionDenied: (() -> Unit)? = null

    private val permissionLauncher = fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            onPermissionGranted ?: listener.onPermissionGranted()
        } else {
            onPermissionDenied ?: listener.onPermissionDenied()
        }
    }

    fun launch(input: String, onPermissionGranted: (() -> Unit)? = null, onPermissionDenied: (() -> Unit)? = null) {
        this.onPermissionGranted = onPermissionGranted
        this.onPermissionDenied = onPermissionDenied
        permissionLauncher.launch(input)
    }

    interface OnPermissionResult {
        fun onPermissionGranted()
        fun onPermissionDenied()
    }
}