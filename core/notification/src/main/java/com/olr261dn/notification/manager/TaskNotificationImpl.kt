package com.olr261dn.notification.manager

import android.content.Context
import com.olr261dn.domain.notification.TaskNotification
import com.olr261dn.notification.R

class TaskNotificationImpl(context: Context) :
    BaseNotification(context), TaskNotification {
    override val channelId: String = "task_channel"
    override val title: Int = R.string.task_title
    override val channelName: Int = R.string.task_channel_name
    override val channelDescription: Int = R.string.task_channel_description
    override val smallIconRes: Int = android.R.drawable.ic_dialog_info
}
