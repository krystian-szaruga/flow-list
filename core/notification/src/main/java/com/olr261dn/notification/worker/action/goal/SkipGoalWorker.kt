package com.olr261dn.notification.worker.action.goal

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.WorkerParameters
import com.olr261dn.domain.notification.SkipGoalCoordinator
import com.olr261dn.notification.worker.action.BaseWorker
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
internal class SkipGoalWorker @AssistedInject constructor(
    private val coordinator: SkipGoalCoordinator,
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
) : BaseWorker(appContext, workerParams) {
    override suspend fun doWork(): Result =
        input.run { if (coordinator.run(id, itemId)) Result.success() else Result.failure() }
}
