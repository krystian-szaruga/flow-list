package com.olr261dn.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.olr261dn.domain.notification.ReminderScheduler
import com.olr261dn.notification.receiver.ReminderReceiver
import com.olr261dn.notification.utils.putReminderExtras
import java.util.UUID

class ReminderSchedulerImpl(private val context: Context): ReminderScheduler {
    override fun schedule(timeInMillis: Long, id: Long, reminderType: String, itemId: UUID) {
        cancelReminder(id)
        getPendingIntent(id, getIntent().putReminderExtras(id, reminderType, itemId))?.let {
            val alarmManager = getAlarmManager()
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                it
            )
        }
    }

    override fun cancelReminder(id: Long) {
        getPendingIntent(
            id, getIntent(), PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )?.let {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(it)
        } ?: Log.d(TAG, "No Reminder Found To Cancel For Id id=$id")
    }

    private fun getIntent(): Intent = Intent(context, ReminderReceiver::class.java)

    private fun getPendingIntent(
        id: Long,
        intent: Intent,
        flag: Int = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    ): PendingIntent? {
        require(id in Int.MIN_VALUE..Int.MAX_VALUE){
            "ID $id is outside Int range and cannot be used with PendingIntent"
        }
        return PendingIntent.getBroadcast(context, id.toInt(), intent, flag)

    }

    private fun getAlarmManager(): AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    companion object {
        private const val TAG = "ReminderScheduler"
    }
}