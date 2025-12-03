package com.olr261dn.viewmodel

import com.olr261dn.domain.model.Schedulable
import kotlin.math.abs

internal fun <T : Schedulable> sortSchedulable(items: List<T>): List<T> =
    System.currentTimeMillis().let { now ->
        items.sortedWith(
            compareBy<T> { it.isDisabled }
                .thenBy { it.effectiveTime < now }
                .thenBy { abs(it.effectiveTime - now) }
        )
    }
