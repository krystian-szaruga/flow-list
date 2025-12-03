package com.olr261dn.impl.scheduler

import com.olr261dn.domain.scheduler.ScheduleTime
import com.olr261dn.domain.scheduler.TimeZoneResolver
import java.time.DateTimeException
import java.time.ZoneId
import java.time.ZonedDateTime

internal class JavaTimeZoneResolver(
    private val zoneId: ZoneId = ZoneId.systemDefault()
) : TimeZoneResolver {

    override fun resolveToValidTime(time: ScheduleTime): ScheduleTime {
        val zonedTime = ZonedDateTime.ofInstant(
            java.time.Instant.ofEpochMilli(time.epochMillis),
            zoneId
        )

        return try {
            zonedTime.withZoneSameLocal(zoneId).toScheduleTime()
        } catch (_: DateTimeException) {
            zonedTime.toLocalDateTime()
                .atZone(zoneId)
                .withEarlierOffsetAtOverlap()
                .toScheduleTime()
        }
    }

    override fun fromEpochMillis(epochMillis: Long): ScheduleTime {
        val zonedTime = ZonedDateTime.ofInstant(
            java.time.Instant.ofEpochMilli(epochMillis),
            zoneId
        )
        return zonedTime.toScheduleTime()
    }

    private fun ZonedDateTime.toScheduleTime(): ScheduleTime {
        return ScheduleTime(
            epochMillis = this.toInstant().toEpochMilli(),
            hour = this.hour,
            minute = this.minute,
            dayOfWeek = this.dayOfWeek
        )
    }
}