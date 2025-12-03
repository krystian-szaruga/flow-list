package com.olr261dn.notification.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.olr261dn.notification.receiver.utils.notifier.WorkerEnqueuer
import com.olr261dn.notification.utils.IntentExtractor

internal class ReminderReceiver (
    private val intentExtractor: IntentExtractor = IntentExtractor,
    private val workerEnqueuer: WorkerEnqueuer = WorkerEnqueuer
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            intentExtractor.extract(intent).let { (id, itemType, itemId) ->
                workerEnqueuer.enqueue(id, itemType, itemId, context.applicationContext)
            }
        }
    }
}
