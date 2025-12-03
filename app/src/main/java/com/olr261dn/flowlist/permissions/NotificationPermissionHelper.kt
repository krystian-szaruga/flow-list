package com.olr261dn.flowlist.permissions

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.olr261dn.resources.R

internal object NotificationPermissionHelper {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun isPermissionGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context, POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun shouldShowRationale(activity: Activity): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(
            activity, POST_NOTIFICATIONS
        )
    }

    fun isAndroid13OrAbove(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }

    fun showPermissionDeniedToast(context: Context) {
        Toast.makeText(
            context,
            context.getString(R.string.notification_permission_denied),
            Toast.LENGTH_SHORT
        ).show()
    }
}

