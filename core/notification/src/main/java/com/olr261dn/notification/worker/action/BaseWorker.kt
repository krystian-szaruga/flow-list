package com.olr261dn.notification.worker.action

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.olr261dn.notification.utils.InputDataWorker
import com.olr261dn.notification.utils.WorkerInputDataExtractor

internal abstract class BaseWorker(
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {
    protected val input: InputDataWorker = WorkerInputDataExtractor.extract(inputData)
}