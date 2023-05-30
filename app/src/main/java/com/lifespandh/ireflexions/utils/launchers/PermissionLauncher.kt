package com.lifespandh.ireflexions.utils.launchers

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class PermissionLauncher(
    fragment: Fragment,
    listener: OnPermissionResult
) {
    private val permissionLauncher = fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            listener.onPermissionGranted()
        } else {
            listener.onPermissionDenied()
        }
    }

    fun launch(input: String) {
        permissionLauncher.launch(input)
    }

    interface OnPermissionResult {
        fun onPermissionGranted()
        fun onPermissionDenied()
    }
}