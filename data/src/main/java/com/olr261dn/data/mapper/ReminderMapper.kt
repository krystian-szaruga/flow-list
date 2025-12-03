package com.olr261dn.data.mapper

import com.olr261dn.data.database.entity.ReminderEntity
import com.olr261dn.domain.model.Reminder
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.model.RepeatPattern
import kotlinx.serialization.json.Json

internal fun Reminder.toEntity(): ReminderEntity =
    ReminderEntity(
        id = id,
        type = type.name,
        repeatPatternJson = repeatPattern?.let { Json.encodeToString(it) },
        scheduledTime = scheduledTime,
        itemId = itemId
    )

internal fun ReminderEntity.toDomain(): Reminder =
    Reminder(
        id = id,
        type = ReminderType.valueOf(type),
        scheduledTime = scheduledTime,
        repeatPattern = repeatPatternJson?.let { Json.decodeFromString<RepeatPattern>(it) },
        itemId = itemId
    )
