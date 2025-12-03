package com.olr261dn.impl.notification.action.goal

import com.olr261dn.domain.notification.SkipGoalCoordinator

internal class SkipGoalCoordinatorImpl(coordinator: ProjectCoordinator):
    ReschedulingProject(coordinator), SkipGoalCoordinator