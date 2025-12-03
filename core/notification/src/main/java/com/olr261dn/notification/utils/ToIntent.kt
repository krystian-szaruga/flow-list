package com.olr261dn.notification.utils

import android.content.Context
import android.content.Intent
import com.olr261dn.domain.notification.NotificationAction
import com.olr261dn.notification.receiver.NotificationActionReceiver


fun NotificationAction.toIntent(context: Context): Intent {
    return Intent(context, NotificationActionReceiver::class.java).apply {
        action = actionType.action
        putReminderExtras(id, reminderType.name, itemId)
    }
}
