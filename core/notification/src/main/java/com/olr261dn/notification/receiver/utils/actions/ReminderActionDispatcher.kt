package com.olr261dn.notification.receiver.utils.actions

import android.content.Context
import android.util.Log
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.notification.ActionType
import com.olr261dn.notification.utils.IntentData

internal object ReminderActionDispatcher {
    fun handle(context: Context, action: ActionType?, intentData: IntentData) {
        when (ReminderType.fromName(intentData.reminderName)) {
            ReminderType.DAILY -> DailyActionHandler.handle(
                context, action, intentData.id, intentData.itemId
            )
            ReminderType.GOAL  -> GoalActionHandler.handle(
                context, action, intentData.id, intentData.itemId
            )
            ReminderType.TASK  -> TaskActionHandler.handle(
                context, action, intentData.id, intentData.itemId
            )
            null -> Log.w(
                "ReminderDispatcher", "Unknown reminder type: ${intentData.reminderName}"
            )
        }
    }
}