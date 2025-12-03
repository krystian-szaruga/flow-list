package com.olr261dn.impl.scheduler

import com.olr261dn.domain.constants.DateConstants
import com.olr261dn.domain.enums.RecurringScheduleType
import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.domain.scheduler.InitialScheduleCalculator
import com.olr261dn.domain.scheduler.TimeProvider
import com.olr261dn.domain.scheduler.TimeZoneResolver
import java.time.DayOfWeek

internal class InitialScheduleCalculatorImpl(
    timeProvider: TimeProvider,
    timeZoneResolver: TimeZoneResolver
) : BaseScheduleCalculator(timeProvider, timeZoneResolver), InitialScheduleCalculator {

    override fun getEffectiveTime(task: RecurringTask): Long {
        val scheduledTime = timeZoneResolver.fromEpochMillis(task.effectiveTime)
        return calculateInitial(
            hour = scheduledTime.hour,
            minute = scheduledTime.minute,
            scheduleType = task.scheduleType,
            customDays = task.customDays
        )
    }

    override fun calculateInitial(
        hour: Int,
        minute: Int,
        scheduleType: RecurringScheduleType,
        customDays: Set<DayOfWeek>
    ): Long {
        val now = timeProvider.now()
        val todayAtTime = timeProvider.todayAt(hour, minute)
        val resolvedTodayAtTime = timeZoneResolver.resolveToValidTime(todayAtTime)
        val candidateTime = if (timeProvider.isAfter(resolvedTodayAtTime, now)) {
            resolvedTodayAtTime
        } else {
            val tomorrow = timeProvider.plusDays(resolvedTodayAtTime, 1)
            timeZoneResolver.resolveToValidTime(tomorrow)
        }
        val allowedDays = when (scheduleType) {
            RecurringScheduleType.EVERY_DAY -> return candidateTime.epochMillis
            RecurringScheduleType.WORKDAYS_ONLY -> DateConstants.WORKDAYS
            RecurringScheduleType.WEEKENDS_ONLY -> DateConstants.WEEKENDS
            RecurringScheduleType.CUSTOM_DAYS -> customDays
        }
        return if (allowedDays.isEmpty()) {
            candidateTime.epochMillis
        } else {
            nextDayProvider.findNextValidDay(candidateTime, allowedDays).epochMillis
        }
    }
}


