package com.olr261dn.notification.rescheduler

import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.notification.ReminderScheduler
import com.olr261dn.domain.notification.Rescheduler
import com.olr261dn.notification.utils.logAndRun
import com.olr261dn.notification.utils.millisToReadableDate
import java.util.UUID
import javax.inject.Inject

class ReschedulerImpl @Inject constructor(
    private val scheduler: ReminderScheduler
): Rescheduler {
    companion object {
        const val TAG = "Rescheduler"
    }
    override fun reschedule(
        newTime: Long,
        notificationId: Long,
        itemId: UUID,
        reminderType: ReminderType
    ) {
        logAndRun(TAG, "Notification has been rescheduled to: ") {
            scheduler.schedule(
                timeInMillis = newTime,
                id = notificationId,
                reminderType = reminderType.name,
                itemId = itemId
            )
            return@logAndRun newTime.millisToReadableDate()
        }
    }

    override fun delayNotification(
        itemId: UUID,
        notificationId: Long,
        reminderType: ReminderType,
        newTime: Long
    ) {
        logAndRun(TAG, "Notification delayed to: ") {
            scheduler.schedule(
                timeInMillis = newTime,
                id = notificationId,
                reminderType = reminderType.name,
                itemId = itemId
            )
            return@logAndRun newTime.millisToReadableDate()
        }
    }
}
