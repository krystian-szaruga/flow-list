package com.olr261dn.impl.notification.action.daily

import com.olr261dn.domain.notification.SkipDailyCoordinator

internal class SkipDailyCoordinatorImpl(
    coordinator: RecurringTaskCoordinator
): ReschedulingDaily(coordinator), SkipDailyCoordinator