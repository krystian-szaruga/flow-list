package com.olr261dn.domain.notification

import com.olr261dn.domain.model.ReminderType
import java.util.UUID

data class NotificationAction(
    val id: Long = 0,
    val actionType: ActionType,
    val reminderType: ReminderType,
    val itemId: UUID,
)
