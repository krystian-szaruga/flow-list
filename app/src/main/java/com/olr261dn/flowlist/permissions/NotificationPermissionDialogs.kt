package com.olr261dn.flowlist.permissions

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.olr261dn.resources.R

internal object NotificationPermissionDialogs {
    fun showPermissionRationaleDialog(
        activity: Activity,
        onPositiveAction: () -> Unit,
    ) {
        showDialog(
            activity,
            title = activity.getString(R.string.notification_rationale_title),
            message = activity.getString(R.string.notification_rationale_message),
            positiveButtonText = activity.getString(R.string.enable_notifications),
            negativeButtonText = activity.getString(R.string.maybe_later),
            onPositiveAction = onPositiveAction
        )
    }

    fun showGoToSettingsDialog(activity: Activity) {
        showDialog(
            activity,
            title = activity.getString(R.string.go_to_settings_title),
            message = activity.getString(R.string.go_to_settings_message),
            positiveButtonText = activity.getString(R.string.open_settings),
            negativeButtonText = activity.getString(R.string.cancel),
            onPositiveAction = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", activity.packageName, null)
                }
                activity.startActivity(intent)
            }
        )
    }

    private fun showDialog(
        activity: Activity,
        title: String,
        message: String,
        positiveButtonText: String,
        negativeButtonText: String? = null,
        onPositiveAction: (() -> Unit)? = null,
        onNegativeAction: (() -> Unit)? = null,
    ) {
        AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { _, _ -> onPositiveAction?.invoke() }
            .setNegativeButton(negativeButtonText) { _, _ -> onNegativeAction?.invoke() }
            .setCancelable(true)
            .show()
    }
}

