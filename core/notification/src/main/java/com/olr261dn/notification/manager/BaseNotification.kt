package com.olr261dn.notification.manager

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.olr261dn.domain.notification.ActionType
import com.olr261dn.domain.notification.NotificationAction
import com.olr261dn.notification.R
import com.olr261dn.notification.utils.toIntent

abstract class BaseNotification(private val context: Context) {
    @get:StringRes
    protected abstract val channelName: Int
    @get:StringRes
    protected abstract val title: Int
    @get:StringRes
    protected abstract val channelDescription: Int
    protected abstract val channelId: String
    protected abstract val smallIconRes: Int
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotification(
        id: Long,
        message: String,
        subtitle: String?,
        actions: List<NotificationAction>,
        deleteAction: NotificationAction
    ) {
        val notification = buildNotification(title, message, subtitle, actions, deleteAction)
        NotificationManagerCompat.from(context).notify(id.toInt(), notification)
    }

    fun cancelNotification(id: Long) {
        NotificationManagerCompat.from(context).cancel(id.toInt())
    }

    private fun buildNotification(
            @StringRes title: Int,
            message: String,
            subtitle: String?,
            actions: List<NotificationAction>,
            deleteAction: NotificationAction
        ): Notification {
            val fullMessage = subtitle?.let {
                context.getString(R.string.linked_to_goal, message, it)
            } ?: message
            createNotificationChannel()
            val builder = NotificationCompat.Builder(context, channelId)
                .setContentTitle(context.getString(title))
                .setContentText(fullMessage)
                .setSmallIcon(smallIconRes)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setDeleteIntent(
                    PendingIntent.getBroadcast(
                        context,
                        deleteAction.id.toInt(),
                        deleteAction.toIntent(context),
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                )
                .setContentIntent(createContentIntent())
                .setAutoCancel(false)

        actions.forEach { action ->
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    action.id.toInt(),
                    action.toIntent(context.applicationContext),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                builder.addAction(0, action.actionType.getLocalizedName(context), pendingIntent)
            }
            return builder.build()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            channelId,
            context.getString(channelName),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = context.getString(channelDescription)
        }
        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    private fun ActionType.getLocalizedName(context: Context): String {
        return when (this) {
            ActionType.DONE -> context.getString(R.string.action_done)
            ActionType.DELAY -> context.getString(R.string.action_delay)
            ActionType.SKIP -> context.getString(R.string.action_skip)
        }
    }

    private fun createContentIntent(): PendingIntent {
        val action = "com.olr261dn.flowlist.ACTION_OPEN_MAIN"
        val intent = Intent(action).apply {
            setPackage(context.packageName)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
