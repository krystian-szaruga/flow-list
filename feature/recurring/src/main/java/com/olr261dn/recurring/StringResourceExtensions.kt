package com.olr261dn.recurring

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.olr261dn.domain.enums.RecurringScheduleType
import com.olr261dn.resources.R
import java.time.DayOfWeek

val DayOfWeek.fullNameRes: Int
    get() = when (this) {
        DayOfWeek.MONDAY -> R.string.monday
        DayOfWeek.TUESDAY -> R.string.tuesday
        DayOfWeek.WEDNESDAY -> R.string.wednesday
        DayOfWeek.THURSDAY -> R.string.thursday
        DayOfWeek.FRIDAY -> R.string.friday
        DayOfWeek.SATURDAY -> R.string.saturday
        DayOfWeek.SUNDAY -> R.string.sunday
    }

val DayOfWeek.shortNameRes: Int
    get() = when (this) {
        DayOfWeek.MONDAY -> R.string.mon_short
        DayOfWeek.TUESDAY -> R.string.tue_short
        DayOfWeek.WEDNESDAY -> R.string.wed_short
        DayOfWeek.THURSDAY -> R.string.thu_short
        DayOfWeek.FRIDAY -> R.string.fri_short
        DayOfWeek.SATURDAY -> R.string.sat_short
        DayOfWeek.SUNDAY -> R.string.sun_short
    }

val RecurringScheduleType.labelRes: Int
    get() = when (this) {
        RecurringScheduleType.EVERY_DAY -> R.string.every_day
        RecurringScheduleType.WORKDAYS_ONLY -> R.string.work_days_only
        RecurringScheduleType.WEEKENDS_ONLY -> R.string.weekends_only
        RecurringScheduleType.CUSTOM_DAYS -> R.string.custom_days
    }

@Composable
fun Set<DayOfWeek>.toShortLabel(): String {
    if (isEmpty()) return stringResource(R.string.select_days)
    return this
        .sortedBy { it.value }
        .map { stringResource(it.shortNameRes) }
        .joinToString(", ")
}