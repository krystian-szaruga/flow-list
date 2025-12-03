package com.olr261dn.domain.model

import com.olr261dn.domain.enums.RecurringScheduleType
import java.time.DayOfWeek
import java.util.UUID

data class RecurringTask (
    override val id: UUID,
    override val title: String = "",
    val description: String = "",
    override val scheduledTime: Long = 0,
    override val delayedTime: Long? = null,
    override val isDisabled: Boolean = true,
    val scheduleType: RecurringScheduleType = RecurringScheduleType.EVERY_DAY,
    val customDays: Set<DayOfWeek> = emptySet()
): Schedulable(id, title, scheduledTime, delayedTime)