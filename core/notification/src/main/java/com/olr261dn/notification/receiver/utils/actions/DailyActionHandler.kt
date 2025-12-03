package com.olr261dn.notification.receiver.utils.actions

import android.content.Context
import android.util.Log
import com.olr261dn.domain.notification.ActionType
import com.olr261dn.notification.worker.action.daily.DelayDailyWorker
import com.olr261dn.notification.worker.action.daily.DoneDailyWorker
import com.olr261dn.notification.worker.action.daily.SkipDailyWorker

internal object DailyActionHandler: ActionHandler() {
    override fun handle(
        context: Context,
        action: ActionType?,
        id: Long,
        itemId: String,
    ) {
        when (action) {
            ActionType.DONE -> { runWorker<DoneDailyWorker>(context, itemId, id) }
            ActionType.DELAY -> { runWorker<DelayDailyWorker>(context, itemId, id) }
            ActionType.SKIP -> { runWorker<SkipDailyWorker>(context, itemId, id) }
            null -> Log.w("DailyHandler", "Unknown action: $action")
        }
    }
}