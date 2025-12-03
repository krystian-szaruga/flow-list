package com.olr261dn.data.mapper

import com.olr261dn.data.database.entity.TaskEntity
import com.olr261dn.domain.model.RepeatPattern
import com.olr261dn.domain.model.Task
import kotlinx.serialization.json.Json

internal fun TaskEntity.toDomain(): Task = Task(
    id = id,
    title = title,
    scheduledTime = scheduledTime,
    delayedTime = delayedTime,
    description = description,
    repeatPattern = Json.decodeFromString<RepeatPattern>(repeatPatternJson),
    goalId = goalId,
    isDisabled = isDisabled,
)

internal fun Task.toEntity(): TaskEntity = TaskEntity(
    id= id,
    goalId = goalId,
    title = title,
    delayedTime = delayedTime,
    repeatPatternJson = Json.encodeToString(repeatPattern),
    description = description,
    isDisabled = isDisabled,
    scheduledTime = scheduledTime
)