package com.olr261dn.notification.worker.display

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.WorkerParameters
import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.domain.notification.RecurringTaskNotificationCoordinator
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
internal class DailyDisplayWorker @AssistedInject constructor(
    coordinator: RecurringTaskNotificationCoordinator,
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
) : BaseDisplayWorker<RecurringTask>(coordinator, appContext, workerParams)
