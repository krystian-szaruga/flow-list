package com.olr261dn.data.mapper

import com.olr261dn.data.database.entity.GoalWithTasksEntity
import com.olr261dn.domain.model.GoalWithTasks

internal fun GoalWithTasksEntity.toDomain(): GoalWithTasks = GoalWithTasks(
    project = goal.toDomain(),
    tasks = tasks.map { it.toDomain() }
)

internal fun GoalWithTasks.toEntity(): GoalWithTasksEntity = GoalWithTasksEntity(
    goal = project.toEntity(),
    tasks = tasks.map { it.toEntity() }
)