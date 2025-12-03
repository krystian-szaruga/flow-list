package com.olr261dn.impl.notification.action

import com.olr261dn.domain.model.Reminder
import com.olr261dn.domain.model.Schedulable
import java.util.UUID

internal abstract class ReschedulingCoordinator<T : Schedulable>(
    coordinator: ItemCoordinator<T>
): ActionCoordinator<T>(coordinator) {

    override suspend fun run(id: Long, itemId: UUID): Boolean {
        return executeItemActions(id, itemId) { item, reminder ->
            completeAndReschedule(item, reminder)
        }
    }
    abstract suspend fun completeAndReschedule(item: T, reminder: Reminder): Boolean
}