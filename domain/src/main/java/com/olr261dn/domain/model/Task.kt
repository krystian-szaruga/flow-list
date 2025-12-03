package com.olr261dn.domain.model

import java.util.UUID

data class Task(
    override val id: UUID,
    override val title: String= "",
    override val scheduledTime: Long = 0,
    override val delayedTime: Long? = null,
    val description: String = "",
    val repeatPattern: RepeatPattern = RepeatPattern.Once,
    val goalId: UUID? = null,
    override val isDisabled: Boolean = false,
): Schedulable(id, title, scheduledTime, delayedTime)