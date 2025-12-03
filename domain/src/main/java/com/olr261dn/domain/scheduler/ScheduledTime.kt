package com.olr261dn.domain.scheduler

import java.time.DayOfWeek

data class ScheduleTime(
    val epochMillis: Long,
    val hour: Int,
    val minute: Int,
    val dayOfWeek: DayOfWeek
) {
    init {
        require(hour in 0..23) { "Hour must be 0-23" }
        require(minute in 0..59) { "Minute must be 0-59" }
    }

    companion object {
        fun fromEpochMillis(millis: Long, hour: Int, minute: Int, dayOfWeek: DayOfWeek): ScheduleTime {
            return ScheduleTime(millis, hour, minute, dayOfWeek)
        }
    }
}
