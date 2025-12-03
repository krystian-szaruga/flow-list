package com.olr261dn.domain.scheduler

interface TimeProvider {
    fun now(): ScheduleTime
    fun todayAt(hour: Int, minute: Int): ScheduleTime
    fun plusDays(time: ScheduleTime, days: Long): ScheduleTime
    fun isAfter(time1: ScheduleTime, time2: ScheduleTime): Boolean
}