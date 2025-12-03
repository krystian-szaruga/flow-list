package com.olr261dn.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val type: String,
    val scheduledTime: Long,
    val repeatPatternJson: String?,
    val itemId: UUID
)