package com.olr261dn.domain.notification

import java.util.UUID

interface ReminderScheduler {
    fun schedule(timeInMillis: Long, id: Long, reminderType: String, itemId: UUID)
    fun cancelReminder(id: Long)
}