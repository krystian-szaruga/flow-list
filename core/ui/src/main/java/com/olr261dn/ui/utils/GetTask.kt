package com.olr261dn.ui.utils

import com.olr261dn.domain.model.RepeatPattern
import com.olr261dn.domain.model.Task
import java.util.UUID

fun getTask(
    id: UUID?,
    title: String,
    description: String,
    goalId: UUID?,
    time: Long,
    repeatPattern: RepeatPattern,
): Task {
    return Task(
        id = id ?: UUID.randomUUID(),
        title = title,
        scheduledTime = time,
        goalId = goalId,
        delayedTime = null,
        description = description,
        isDisabled = false,
        repeatPattern = repeatPattern
    )
}