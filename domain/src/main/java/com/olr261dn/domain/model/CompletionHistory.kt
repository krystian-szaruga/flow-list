package com.olr261dn.domain.model

import java.util.UUID

data class CompletionHistory(
    val id : UUID,
    val recurringTaskId: UUID? = null,
    val taskId: UUID? = null,
    val projectId: UUID? = null,
    val date: Long,
    val isCompleted: Boolean,
    val markedAt: Long
)