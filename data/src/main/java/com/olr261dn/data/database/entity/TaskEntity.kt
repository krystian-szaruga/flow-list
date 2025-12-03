package com.olr261dn.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ProjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["goalId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["goalId"])]
)
internal data class TaskEntity(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val goalId: UUID?,
    val title: String,
    val delayedTime: Long?,
    val repeatPatternJson: String,
    val description: String,
    val isDisabled: Boolean,
    val scheduledTime: Long
)
