package com.olr261dn.impl.scheduler

import com.olr261dn.domain.scheduler.ScheduleTime
import com.olr261dn.domain.scheduler.TimeProvider
import java.time.ZoneId
import java.time.ZonedDateTime

internal class SystemTimeProvider(
    private val zoneId: ZoneId = ZoneId.systemDefault()
) : TimeProvider {

    override fun now(): ScheduleTime {
        return ZonedDateTime.now(zoneId).toScheduleTime()
    }

    override fun todayAt(hour: Int, minute: Int): ScheduleTime {
        val now = ZonedDateTime.now(zoneId)
        val adjusted = now
            .withHour(hour)
            .withMinute(minute)
            .withSecond(0)
            .withNano(0)

        return adjusted.toScheduleTime()
    }

    override fun plusDays(time: ScheduleTime, days: Long): ScheduleTime {
        val zonedTime = time.toZonedDateTime(zoneId)
        return zonedTime.plusDays(days).toScheduleTime()
    }

    override fun isAfter(time1: ScheduleTime, time2: ScheduleTime): Boolean {
        return time1.epochMillis > time2.epochMillis
    }

    private fun ZonedDateTime.toScheduleTime(): ScheduleTime {
        return ScheduleTime(
            epochMillis = this.toInstant().toEpochMilli(),
            hour = this.hour,
            minute = this.minute,
            dayOfWeek = this.dayOfWeek
        )
    }

    private fun ScheduleTime.toZonedDateTime(zoneId: ZoneId): ZonedDateTime {
        return ZonedDateTime.ofInstant(
            java.time.Instant.ofEpochMilli(this.epochMillis),
            zoneId
        )
    }
}