package com.olr261dn.notification.worker.display

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.olr261dn.domain.model.Schedulable
import com.olr261dn.domain.notification.DisplayerCoordinator
import com.olr261dn.notification.utils.WorkerInputDataExtractor

internal abstract class BaseDisplayWorker<T: Schedulable> (
    private val coordinator: DisplayerCoordinator,
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result =
        WorkerInputDataExtractor.extract(inputData).let { input ->
            Result.success().takeIf {
                coordinator.displayNotification(input.id, input.itemId)
            } ?: Result.failure()
        }
}