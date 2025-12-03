package com.olr261dn.domain.constants

import java.time.DayOfWeek

object DateConstants {
    val WORKDAYS: Set<DayOfWeek> = setOf(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY
    )

    val WEEKENDS: Set<DayOfWeek> = setOf(
        DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY
    )
}
