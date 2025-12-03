package com.olr261dn.impl.scheduler

import com.olr261dn.domain.scheduler.ScheduleTime
import com.olr261dn.domain.scheduler.TimeProvider
import com.olr261dn.domain.scheduler.TimeZoneResolver
import java.time.DayOfWeek

internal class NextDayProvider (
    private val timeProvider: TimeProvider,
    private val timeZoneResolver: TimeZoneResolver
){
    fun findNextValidDay(
        start: ScheduleTime,
        allowedDays: Set<DayOfWeek>
    ): ScheduleTime {
        require(allowedDays.isNotEmpty()) { "allowedDays must not be empty" }
        if (start.dayOfWeek in allowedDays) return start
        for (offset in 1..7) {
            var current = timeProvider.plusDays(start, offset.toLong())
            current = timeZoneResolver.resolveToValidTime(current)
            if (current.dayOfWeek in allowedDays) {
                return current
            }
        }
        return start
    }
}