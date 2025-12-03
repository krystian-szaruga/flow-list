package com.olr261dn.impl.notification.action.task

import com.olr261dn.domain.notification.SkipTaskCoordinator

internal class SkipTaskCoordinatorImpl(coordinator: TaskCoordinator):
    ReschedulingTask(coordinator), SkipTaskCoordinator