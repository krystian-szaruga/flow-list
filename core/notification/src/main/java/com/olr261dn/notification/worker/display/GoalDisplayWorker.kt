package com.olr261dn.notification.worker.display

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.WorkerParameters
import com.olr261dn.domain.model.Project
import com.olr261dn.domain.notification.GoalNotificationCoordinator
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
internal class GoalDisplayWorker @AssistedInject constructor(
    coordinator: GoalNotificationCoordinator,
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
) : BaseDisplayWorker<Project>(coordinator, appContext, workerParams)