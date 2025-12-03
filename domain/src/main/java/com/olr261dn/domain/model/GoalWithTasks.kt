package com.olr261dn.domain.model

data class GoalWithTasks(
    val project: Project,
    val tasks: List<Task>
)