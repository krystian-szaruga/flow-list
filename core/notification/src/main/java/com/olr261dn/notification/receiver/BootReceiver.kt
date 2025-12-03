package com.olr261dn.notification.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.olr261dn.notification.worker.BootWorker

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.i("BootReceiver", "📱 Device rebooted — scheduling BootWorker")
            val workRequest = OneTimeWorkRequestBuilder<BootWorker>().build()
            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}