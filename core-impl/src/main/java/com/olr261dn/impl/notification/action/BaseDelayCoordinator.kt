package com.olr261dn.impl.notification.action

import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.model.Schedulable
import com.olr261dn.impl.notification.action.utils.DelayableCoordinator
import kotlinx.coroutines.flow.firstOrNull
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID
import kotlin.time.Duration.Companion.minutes

internal abstract class BaseDelayCoordinator<T : Schedulable>(
    coordinator: ItemCoordinator<T>
) : ActionCoordinator<T>(coordinator) {
    protected abstract val parentCoordinator: DelayableCoordinator<T>
    protected abstract fun updateItemWithDelay(item: T, delayedTime: Long): T
    protected abstract fun getReminderType(): ReminderType

    override suspend fun run(id: Long, itemId: UUID): Boolean {
        return coordinator.getAction().getById.execute(itemId).firstOrNull()?.let { item ->
            val delayedTime = calculateDelayedTime(item)
            coordinator.dismissNotification(id)
            coordinator.delayNotification(
                notificationId = id,
                itemID = itemId,
                reminderType = getReminderType(),
                newTime = delayedTime
            )
            coordinator.getAction().update.execute(updateItemWithDelay(item, delayedTime))
            true
        } ?: false
    }

    protected suspend fun calculateDelayedTime(item: T): Long {
        val delayMillis = parentCoordinator.getDelayValue()
        val now = System.currentTimeMillis()
        val threshold = 2.minutes.inWholeMilliseconds
        val baseTimeMillis = item.effectiveTime
        val safeBaseTime = if (now - baseTimeMillis > threshold) now else baseTimeMillis
        return ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(safeBaseTime),
            ZoneId.systemDefault()
        )
            .plus(Duration.ofMillis(delayMillis))
            .toInstant()
            .toEpochMilli()
    }
}