package com.olr261dn.impl.notification.action

import com.olr261dn.domain.model.Reminder
import com.olr261dn.domain.model.Schedulable
import java.util.UUID

internal abstract class ActionCoordinator<T: Schedulable>(protected val coordinator: ItemCoordinator<T>) {
    abstract suspend fun run(id: Long, itemId: UUID): Boolean

    protected suspend fun executeItemActions(
        id: Long,
        itemId: UUID,
        block: suspend (T, Reminder) -> Boolean
    ): Boolean {
        coordinator.dismissNotification(id)
        val data = fetchItemAndReminder(id, itemId) ?: return false
        return block(data.item, data.reminder)
    }

    private suspend fun fetchItemAndReminder(id: Long, itemId: UUID): ItemWithReminder<T>? {
        return coordinator.getItem(itemId)?.let { item ->
            coordinator.getReminder(id)?.let { reminder ->
                ItemWithReminder(item, reminder)
            }
        }
    }

    private data class ItemWithReminder<T>(val item: T, val reminder: Reminder)
}