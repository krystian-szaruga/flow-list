package com.olr261dn.impl.notification.action.goal

import com.olr261dn.domain.notification.DoneGoalCoordinator

internal class DoneGoalCoordinatorImpl(coordinator: ProjectCoordinator):
    ReschedulingProject(coordinator), DoneGoalCoordinator