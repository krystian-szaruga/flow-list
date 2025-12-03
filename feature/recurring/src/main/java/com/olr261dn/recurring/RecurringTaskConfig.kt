package com.olr261dn.recurring

import com.olr261dn.domain.enums.RecurringScheduleType
import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.model.RepeatPattern
import com.olr261dn.ui.compose.base.ReminderConfig
import java.time.DayOfWeek

internal object RecurringTaskConfig {
    val reminderConfig: ReminderConfig<RecurringTask> = DailyTaskReminderConfig

    private object DailyTaskReminderConfig: ReminderConfig<RecurringTask> {
        private val WORKDAYS = setOf(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
        )

        private val WEEKENDS = setOf(
            DayOfWeek.SATURDAY,
            DayOfWeek.SUNDAY
        )

        override fun getRepeatPattern(item: RecurringTask): RepeatPattern =
            when (item.scheduleType) {
                RecurringScheduleType.EVERY_DAY -> RepeatPattern.Daily
                RecurringScheduleType.WORKDAYS_ONLY -> RepeatPattern.CustomDays(WORKDAYS)
                RecurringScheduleType.WEEKENDS_ONLY -> RepeatPattern.CustomDays(WEEKENDS)
                RecurringScheduleType.CUSTOM_DAYS -> RepeatPattern.CustomDays(item.customDays)
            }

        override fun getType(): ReminderType = ReminderType.DAILY
    }
}
