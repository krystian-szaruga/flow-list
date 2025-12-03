package com.olr261dn.notification.receiver.utils.notifier

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

internal object WorkerEnqueuer {
    fun enqueue(
        id: Long,
        itemType: String,
        itemId: String,
        applicationContext: Context
    ) {
        WorkerFactory.getWorker(itemType)?.let { clazz ->
            WorkManager.Companion.getInstance(applicationContext).enqueue(
                buildOneTimeRequest(clazz, id, itemId)
            )
        }
    }

    private fun buildOneTimeRequest(
        clazz: Class<out CoroutineWorker>,
        id: Long,
        itemId: String
    ): OneTimeWorkRequest = OneTimeWorkRequest.Builder(clazz)
        .setInputData(
            Data.Builder()
                .putLong("id", id)
                .putString("itemId", itemId)
                .build()
        )
        .build()
}