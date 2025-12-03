package com.olr261dn.impl.notification.action.utils

import com.olr261dn.domain.model.Schedulable

internal interface DelayableCoordinator<T : Schedulable> {
    suspend fun getDelayValue(): Long
}