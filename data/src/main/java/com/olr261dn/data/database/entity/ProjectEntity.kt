package com.olr261dn.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
internal data class ProjectEntity(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val title: String,
    val scheduledTime: Long,
    val delayedTime: Long?,
    val description: String,
    val createdAt: Long,
    val isDisabled: Boolean,
)
