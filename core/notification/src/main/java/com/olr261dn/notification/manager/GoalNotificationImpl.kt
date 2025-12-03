package com.olr261dn.notification.manager

import android.content.Context
import com.olr261dn.domain.notification.GoalNotification
import com.olr261dn.notification.R

class GoalNotificationImpl(context: Context) :
    BaseNotification(context), GoalNotification {
    override val channelId: String = "goal_channel"
    override val title: Int = R.string.goal_title
    override val channelName: Int = R.string.goal_channel_name
    override val channelDescription: Int = R.string.goal_channel_description
    override val smallIconRes: Int = android.R.drawable.ic_dialog_info
}