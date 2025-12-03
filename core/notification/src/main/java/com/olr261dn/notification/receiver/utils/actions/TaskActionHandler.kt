package com.olr261dn.notification.receiver.utils.actions

import android.content.Context
import android.util.Log
import com.olr261dn.domain.notification.ActionType
import com.olr261dn.notification.worker.action.task.DelayTaskWorker
import com.olr261dn.notification.worker.action.task.DoneTaskWorker
import com.olr261dn.notification.worker.action.task.SkipTaskWorker


internal object TaskActionHandler: ActionHandler() {
    override fun handle(
        context: Context,
        action: ActionType?,
        id: Long,
        itemId: String
    ) {
        when (action) {
            ActionType.DONE -> { runWorker<DoneTaskWorker>(context, itemId, id) }
            ActionType.DELAY -> { runWorker<DelayTaskWorker>(context, itemId, id) }
            ActionType.SKIP -> { runWorker<SkipTaskWorker>(context, itemId, id) }
            null -> Log.w("DailyHandler", "Unknown action: $action")
        } }
}