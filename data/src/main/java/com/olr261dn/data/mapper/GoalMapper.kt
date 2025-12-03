package com.olr261dn.data.mapper

import com.olr261dn.data.database.entity.ProjectEntity
import com.olr261dn.domain.model.Project


internal fun ProjectEntity.toDomain(): Project = Project(
    id = id,
    title = title,
    description = description,
    createdAt = createdAt,
    isDisabled = isDisabled,
    scheduledTime = scheduledTime,
    delayedTime = delayedTime
)

internal fun Project.toEntity(): ProjectEntity = ProjectEntity(
    id = id,
    title = title,
    description = description,
    createdAt = createdAt,
    isDisabled = isDisabled,
    scheduledTime = scheduledTime,
    delayedTime = delayedTime
)
