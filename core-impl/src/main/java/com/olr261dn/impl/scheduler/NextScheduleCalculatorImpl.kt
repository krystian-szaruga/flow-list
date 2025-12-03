package com.olr261dn.impl.scheduler

import com.olr261dn.domain.model.RepeatPattern
import com.olr261dn.domain.scheduler.NextScheduleCalculator
import com.olr261dn.domain.scheduler.TimeProvider
import com.olr261dn.domain.scheduler.TimeZoneResolver

internal class NextScheduleCalculatorImpl(
    timeProvider: TimeProvider,
    timeZoneResolver: TimeZoneResolver
) : BaseScheduleCalculator(timeProvider, timeZoneResolver), NextScheduleCalculator {
    override fun calculateNext(
        lastScheduledTime: Long,
        pattern: RepeatPattern?
    ): Long? {
        val lastScheduled = timeZoneResolver.fromEpochMillis(lastScheduledTime)
        return when (pattern) {
            null, is RepeatPattern.Once -> null
            is RepeatPattern.Daily -> {
                val next = timeProvider.plusDays(lastScheduled, 1)
                timeZoneResolver.resolveToValidTime(next).epochMillis
            }
            is RepeatPattern.EveryXDays -> {
                val next = timeProvider.plusDays(lastScheduled, pattern.interval)
                timeZoneResolver.resolveToValidTime(next).epochMillis
            }
            is RepeatPattern.CustomDays -> {
                val nextDay = timeProvider.plusDays(lastScheduled, 1)
                val resolved = timeZoneResolver.resolveToValidTime(nextDay)

                if (pattern.daysOfWeek.isEmpty()) {
                    resolved.epochMillis
                } else {
                    nextDayProvider.findNextValidDay(resolved, pattern.daysOfWeek).epochMillis
                }
            }
        }
    }
}