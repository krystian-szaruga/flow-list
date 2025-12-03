package com.olr261dn.data.mapper

import com.olr261dn.data.database.entity.CompletionHistoryEntity
import com.olr261dn.domain.model.CompletionHistory

internal fun CompletionHistoryEntity.toDomain(): CompletionHistory = CompletionHistory(
    id = id,
    recurringTaskId = recurringTaskId,
    taskId = taskId,
    projectId = projectId,
    date = date,
    isCompleted = isCompleted,
    markedAt = markedAt
)

internal fun CompletionHistory.toEntity(): CompletionHistoryEntity = CompletionHistoryEntity(
    id = id,
    recurringTaskId = recurringTaskId,
    taskId = taskId,
    projectId = projectId,
    date = date,
    isCompleted = isCompleted,
    markedAt = markedAt
)
