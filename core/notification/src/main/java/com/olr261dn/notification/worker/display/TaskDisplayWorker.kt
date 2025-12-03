package com.olr261dn.notification.worker.display

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.WorkerParameters
import com.olr261dn.domain.model.Task
import com.olr261dn.domain.notification.TaskNotificationCoordinator
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
internal class TaskDisplayWorker @AssistedInject constructor(
    coordinator: TaskNotificationCoordinator,
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
) : BaseDisplayWorker<Task>(coordinator, appContext, workerParams)