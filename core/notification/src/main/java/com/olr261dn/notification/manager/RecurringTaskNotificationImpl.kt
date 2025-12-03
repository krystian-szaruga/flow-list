package com.olr261dn.notification.manager

import android.content.Context
import com.olr261dn.domain.notification.DailyTaskNotification
import com.olr261dn.notification.R

class RecurringTaskNotificationImpl(context: Context) :
    BaseNotification(context), DailyTaskNotification {
    override val channelId: String = "recurring_task_channel"
    override val title: Int = R.string.recurring_task_title
    override val channelName: Int = R.string.recurring_task_channel_name
    override val channelDescription: Int = R.string.recurring_task_channel_description
    override val smallIconRes: Int = android.R.drawable.ic_dialog_info
}