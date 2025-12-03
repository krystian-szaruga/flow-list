package com.olr261dn.domain.scheduler

import com.olr261dn.domain.enums.RecurringScheduleType
import com.olr261dn.domain.model.RecurringTask
import java.time.DayOfWeek

interface InitialScheduleCalculator {
    fun getEffectiveTime(task: RecurringTask): Long
    fun calculateInitial(
        hour: Int,
        minute: Int,
        scheduleType: RecurringScheduleType,
        customDays: Set<DayOfWeek>
    ): Long
}