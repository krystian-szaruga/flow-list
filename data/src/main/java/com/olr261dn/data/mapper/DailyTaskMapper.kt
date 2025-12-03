package com.olr261dn.data.mapper

import com.olr261dn.data.database.entity.RecurringTaskEntity
import com.olr261dn.domain.enums.RecurringScheduleType
import com.olr261dn.domain.model.RecurringTask
import java.time.DayOfWeek


internal fun RecurringTaskEntity.toDomain(): RecurringTask = RecurringTask(
    id = id,
    title = title,
    description = description,
    scheduledTime = scheduledTime,
    isDisabled = isDisabled,
    scheduleType = RecurringScheduleType.valueOf(scheduleType),
    customDays = if (customDays.isBlank()) {
        emptySet()
    } else {
        customDays.split(",").map { DayOfWeek.valueOf(it) }.toSet()
    },
    delayedTime = delayedTime
)

internal fun RecurringTask.toEntity(): RecurringTaskEntity = RecurringTaskEntity(
    id = id,
    title = title,
    description = description,
    scheduledTime = scheduledTime,
    isDisabled = isDisabled,
    scheduleType = scheduleType.name,
    customDays = customDays.joinToString(",") { it.name },
    delayedTime = delayedTime
)