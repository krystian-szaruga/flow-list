package com.olr261dn.project

import com.olr261dn.domain.model.Project
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.model.RepeatPattern
import com.olr261dn.ui.compose.base.ReminderConfig

internal object ProjectReminderConfig: ReminderConfig<Project> {
    override fun getRepeatPattern(item: Project): RepeatPattern = RepeatPattern.Once
    override fun getType(): ReminderType = ReminderType.GOAL
}