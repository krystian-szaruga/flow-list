package com.olr261dn.impl.notification.action.task

import com.olr261dn.domain.notification.DoneTaskCoordinator

internal class DoneTaskCoordinatorImpl(coordinator: TaskCoordinator):
    ReschedulingTask(coordinator), DoneTaskCoordinator