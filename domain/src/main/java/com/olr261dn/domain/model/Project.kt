package com.olr261dn.domain.model

import java.util.UUID

data class Project(
    override val id: UUID,
    override val title: String = "",
    override val scheduledTime: Long = 0L,
    override val delayedTime: Long? = null,
    val description: String = "",
    val createdAt: Long = 0L,
    override val isDisabled: Boolean = false,
): Schedulable(id, title, scheduledTime, delayedTime, isDisabled)