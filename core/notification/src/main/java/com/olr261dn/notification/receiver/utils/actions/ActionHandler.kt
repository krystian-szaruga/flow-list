package com.olr261dn.notification.receiver.utils.actions

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.olr261dn.domain.notification.ActionType

internal abstract class ActionHandler {
    abstract fun handle(context: Context, action: ActionType?, id: Long, itemId: String)

    protected inline fun <reified T : CoroutineWorker> runWorker(
        context: Context,
        itemId: String,
        id: Long
    ) {
        WorkManager.getInstance(context).enqueue(
            OneTimeWorkRequestBuilder<T>()
                .setInputData(
                    Data.Builder()
                        .putString("itemId", itemId)
                        .putLong("id", id)
                        .build()
                ).build()
        )
    }
}