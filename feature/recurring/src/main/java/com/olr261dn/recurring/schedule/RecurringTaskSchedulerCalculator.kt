package com.olr261dn.recurring.schedule

import com.olr261dn.domain.enums.RecurringScheduleType
import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.domain.model.RepeatPattern
import java.time.DateTimeException
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class RecurringTaskSchedulerCalculator {
    companion object {
        private val WEEKEND_DAYS = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
        private val WORK_DAYS = DayOfWeek.entries.toSet() - WEEKEND_DAYS
        private val SYSTEM_ZONE = ZoneId.systemDefault()
    }

    fun getEffectiveTime(task: RecurringTask): Long {
        val scheduledTime = ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(task.effectiveTime),
            SYSTEM_ZONE
        )

        return calculateNextOccurrence(
            hour = scheduledTime.hour,
            minute = scheduledTime.minute,
            scheduleType = task.scheduleType,
            customDays = task.customDays
        )
    }

    fun calculateNextOccurrence(
        hour: Int,
        minute: Int,
        scheduleType: RecurringScheduleType,
        customDays: Set<DayOfWeek>
    ): Long {
        val now = ZonedDateTime.now(SYSTEM_ZONE)
        val todayAtTime = now
            .withHour(hour)
            .withMinute(minute)
            .withSecond(0)
            .withNano(0)
            .resolveToValidTime()

        val candidateTime = if (todayAtTime.isAfter(now)) {
            todayAtTime
        } else {
            todayAtTime.plusDays(1).resolveToValidTime()
        }

        val allowedDays = when (scheduleType) {
            RecurringScheduleType.EVERY_DAY -> return candidateTime.toEpochMilli()
            RecurringScheduleType.WORKDAYS_ONLY -> WORK_DAYS
            RecurringScheduleType.WEEKENDS_ONLY -> WEEKEND_DAYS
            RecurringScheduleType.CUSTOM_DAYS -> customDays
        }

        return if (allowedDays.isEmpty()) {
            candidateTime.toEpochMilli()
        } else {
            findNextValidDay(candidateTime, allowedDays).toEpochMilli()
        }
    }

    fun calculateNextFromPattern(lastScheduledTime: Long, pattern: RepeatPattern?): Long? {
        val lastScheduled = ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(lastScheduledTime),
            SYSTEM_ZONE
        )

        return when (pattern) {
            null, is RepeatPattern.Once -> null
            is RepeatPattern.Daily -> {
                lastScheduled.plusDays(1).resolveToValidTime().toEpochMilli()
            }
            is RepeatPattern.EveryXDays -> {
                lastScheduled.plusDays(pattern.interval).resolveToValidTime().toEpochMilli()
            }
            is RepeatPattern.CustomDays -> {
                val nextDay = lastScheduled.plusDays(1).resolveToValidTime()
                if (pattern.daysOfWeek.isEmpty()) {
                    nextDay.toEpochMilli()
                } else {
                    findNextValidDay(nextDay, pattern.daysOfWeek).toEpochMilli()
                }
            }
        }
    }

    private fun findNextValidDay(
        start: ZonedDateTime,
        allowedDays: Set<DayOfWeek>
    ): ZonedDateTime {
        require(allowedDays.isNotEmpty()) { "allowedDays must not be empty" }

        if (start.dayOfWeek in allowedDays) return start

        val daysToAdd = (1..7).first { offset ->
            start.plusDays(offset.toLong()).dayOfWeek in allowedDays
        }

        return start.plusDays(daysToAdd.toLong()).resolveToValidTime()
    }

    private fun ZonedDateTime.resolveToValidTime(): ZonedDateTime {
        return try {
            this.withZoneSameLocal(SYSTEM_ZONE)
        } catch (_: DateTimeException) {
            this.toLocalDateTime()
                .atZone(SYSTEM_ZONE)
                .withEarlierOffsetAtOverlap()
        }
    }

    private fun ZonedDateTime.toEpochMilli(): Long =
        this.toInstant().toEpochMilli()
}