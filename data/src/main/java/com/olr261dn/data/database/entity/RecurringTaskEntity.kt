package com.olr261dn.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
internal data class RecurringTaskEntity(
    @PrimaryKey(autoGenerate = false) val id: UUID,
    val title: String,
    val description: String,
    val scheduledTime: Long,
    val delayedTime: Long? = null,
    val isDisabled: Boolean,
    val scheduleType: String,
    val customDays: String
)
