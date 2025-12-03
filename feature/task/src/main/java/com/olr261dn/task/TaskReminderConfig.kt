package com.olr261dn.task

import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.model.RepeatPattern
import com.olr261dn.domain.model.Task
import com.olr261dn.ui.compose.base.ReminderConfig

internal object TaskReminderConfig: ReminderConfig<Task> {
    override fun getRepeatPattern(item: Task): RepeatPattern = RepeatPattern.Once
    override fun getType(): ReminderType = ReminderType.TASK
}
