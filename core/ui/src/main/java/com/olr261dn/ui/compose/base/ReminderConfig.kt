package com.olr261dn.ui.compose.base

import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.model.RepeatPattern
import com.olr261dn.domain.model.Schedulable

interface ReminderConfig<T: Schedulable> {
    fun getRepeatPattern(item: T): RepeatPattern
    fun getType(): ReminderType
}