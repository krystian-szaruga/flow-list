package com.olr261dn.impl.notification.action.daily

import com.olr261dn.domain.notification.DoneDailyCoordinator

internal class DoneDailyCoordinatorImpl(
    coordinator: RecurringTaskCoordinator
): ReschedulingDaily(coordinator), DoneDailyCoordinator