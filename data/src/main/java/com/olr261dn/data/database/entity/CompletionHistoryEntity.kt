package com.olr261dn.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RecurringTaskEntity::class,
            parentColumns = ["id"],
            childColumns = ["recurringTaskId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TaskEntity::class,
            parentColumns = ["id"],
            childColumns = ["taskId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["projectId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["recurringTaskId"]), Index(value = ["taskId"]), Index(value = ["projectId"])]
)
internal data class CompletionHistoryEntity(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val recurringTaskId: UUID? = null,
    val taskId: UUID? = null,
    val projectId: UUID? = null,
    val date: Long,
    val isCompleted: Boolean,
    val markedAt: Long
)