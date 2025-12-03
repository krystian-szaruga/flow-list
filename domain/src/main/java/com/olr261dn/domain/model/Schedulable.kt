package com.olr261dn.domain.model

import java.util.UUID

sealed class Schedulable(
    open val id: UUID,
    open val title: String,
    open val scheduledTime: Long,
    open val delayedTime: Long?,
    open val isDisabled: Boolean = false
) {
    val effectiveTime: Long
        get() = delayedTime ?: scheduledTime
}