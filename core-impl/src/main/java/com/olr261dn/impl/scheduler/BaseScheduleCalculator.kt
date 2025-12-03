package com.olr261dn.impl.scheduler

import com.olr261dn.domain.scheduler.TimeProvider
import com.olr261dn.domain.scheduler.TimeZoneResolver

internal abstract class BaseScheduleCalculator(
    protected val timeProvider: TimeProvider,
    protected val timeZoneResolver: TimeZoneResolver
) {
    protected val nextDayProvider = NextDayProvider(timeProvider, timeZoneResolver)
}