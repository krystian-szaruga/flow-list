package com.olr261dn.notification.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.olr261dn.domain.notification.ActionType
import com.olr261dn.notification.receiver.utils.actions.ReminderActionDispatcher
import com.olr261dn.notification.utils.IntentExtractor

internal class NotificationActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val intentData = IntentExtractor.extract(intent)
        val action = ActionType.fromAction(intent.action)
        ReminderActionDispatcher.handle(context, action, intentData)
    }
}








