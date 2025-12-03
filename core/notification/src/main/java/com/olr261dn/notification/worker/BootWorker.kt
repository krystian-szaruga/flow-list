package com.olr261dn.notification.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.olr261dn.domain.notification.BootCoordinator
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
internal class BootWorker @AssistedInject constructor(
    private val coordinator: BootCoordinator,
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return if (coordinator.run()) Result.success() else Result.failure()
    }
}