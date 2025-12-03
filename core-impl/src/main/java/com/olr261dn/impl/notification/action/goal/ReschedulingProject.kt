package com.olr261dn.impl.notification.action.goal

import com.olr261dn.domain.model.Project
import com.olr261dn.domain.model.Reminder
import com.olr261dn.impl.notification.action.ReschedulingCoordinator


internal abstract class ReschedulingProject(
    coordinator: ProjectCoordinator
): ReschedulingCoordinator<Project>(coordinator) {
    override suspend fun completeAndReschedule(item: Project, reminder: Reminder): Boolean {
        coordinator.updateItem(item.copy(
            isDisabled = true,
            delayedTime = null
        ))
        coordinator.addCompletionHistoryRecord(item)
        return true
    }
}