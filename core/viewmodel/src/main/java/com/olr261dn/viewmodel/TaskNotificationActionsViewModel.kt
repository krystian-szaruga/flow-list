package com.olr261dn.viewmodel

import com.olr261dn.domain.notification.DoneTaskCoordinator
import com.olr261dn.domain.notification.SkipTaskCoordinator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskNotificationActionsViewModel @Inject constructor(
    done: DoneTaskCoordinator,
    skip: SkipTaskCoordinator,
): NotificationActionsViewModel(done, skip)