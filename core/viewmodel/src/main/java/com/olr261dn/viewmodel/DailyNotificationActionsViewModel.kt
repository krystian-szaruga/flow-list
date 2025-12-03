package com.olr261dn.viewmodel

import com.olr261dn.domain.notification.DoneDailyCoordinator
import com.olr261dn.domain.notification.SkipDailyCoordinator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DailyNotificationActionsViewModel @Inject constructor(
    done: DoneDailyCoordinator,
    skip: SkipDailyCoordinator,
): NotificationActionsViewModel(done, skip)


