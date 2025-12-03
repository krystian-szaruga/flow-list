package com.olr261dn.ui.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun Long.toHourMinute(): Pair<Int, Int> {
    val instant = Instant.ofEpochMilli(this)
    val time = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime()
    return time.hour to time.minute
}