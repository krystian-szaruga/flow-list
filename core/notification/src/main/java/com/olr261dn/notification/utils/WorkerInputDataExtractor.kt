package com.olr261dn.notification.utils

import androidx.work.Data

internal object WorkerInputDataExtractor {
    fun extract(inputData: Data): InputDataWorker = InputDataWorker(
        inputData.getLong("id", -1),
        inputData.getString("itemId") ?: ""
    )
}