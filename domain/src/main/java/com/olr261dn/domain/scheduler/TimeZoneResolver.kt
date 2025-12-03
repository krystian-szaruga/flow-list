package com.olr261dn.domain.scheduler

interface TimeZoneResolver {
    fun resolveToValidTime(time: ScheduleTime): ScheduleTime
    fun fromEpochMillis(epochMillis: Long): ScheduleTime
}