package com.olr261dn.ui.compose.base

import com.olr261dn.domain.model.Schedulable

internal interface Config<T : Schedulable> {
    val config: ReminderConfig<T>
}
