package com.olr261dn.domain.model

import java.util.UUID

data class Reminder(
    val id: Long = 0L,
    val type: ReminderType,
    val scheduledTime: Long,
    val repeatPattern: RepeatPattern? = null,
    val itemId: UUID
)

