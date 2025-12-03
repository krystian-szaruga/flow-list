package com.olr261dn.domain.notification

import com.olr261dn.domain.model.ReminderType
import java.util.UUID

interface Rescheduler {
    fun reschedule(
        newTime: Long,
        notificationId: Long,
        itemId: UUID,
        reminderType: ReminderType
    )

    fun delayNotification(
        itemId: UUID,
        notificationId: Long,
        reminderType: ReminderType,
        newTime: Long
    )
}