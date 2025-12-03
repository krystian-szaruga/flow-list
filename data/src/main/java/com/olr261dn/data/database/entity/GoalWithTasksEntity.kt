package com.olr261dn.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

internal data class GoalWithTasksEntity(
    @Embedded val goal: ProjectEntity,

    @Relation(
        parentColumn = "id",      // from GoalEntity
        entityColumn = "goalId"   // from TaskEntity
    )
    val tasks: List<TaskEntity>
)