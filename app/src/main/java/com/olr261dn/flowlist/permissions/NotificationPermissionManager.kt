package com.olr261dn.flowlist.permissions

import android.Manifest.permission.POST_NOTIFICATIONS
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity


class NotificationPermissionManager(private val activity: FragmentActivity) {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    fun setup() {
        requestPermissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            handlePermissionResult(isGranted)
        }
    }

    fun checkAndRequest() {
        if (!NotificationPermissionHelper.isAndroid13OrAbove()) return
        if (NotificationPermissionHelper.isPermissionGranted(activity)) return

        when {
            NotificationPermissionHelper.shouldShowRationale(activity) -> {
                NotificationPermissionDialogs.showPermissionRationaleDialog(activity) {
                    requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                }
            }

            else -> requestPermissionLauncher.launch(POST_NOTIFICATIONS)
        }
    }

    private fun handlePermissionResult(isGranted: Boolean) {
        when {
            isGranted -> return
            !activity.shouldShowRequestPermissionRationale(POST_NOTIFICATIONS) -> {
                NotificationPermissionDialogs.showGoToSettingsDialog(activity)
            }

            else -> {
                NotificationPermissionHelper.showPermissionDeniedToast(activity)
            }
        }
    }
}