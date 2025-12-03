package com.olr261dn.notification.receiver.utils.actions

import android.content.Context
import android.util.Log
import com.olr261dn.domain.notification.ActionType
import com.olr261dn.notification.worker.action.goal.DoneGoalWorker
import com.olr261dn.notification.worker.action.goal.SkipGoalWorker

internal object GoalActionHandler: ActionHandler() {
    override fun handle(
        context: Context,
        action: ActionType?,
        id: Long,
        itemId: String
    ) {
        when (action) {
            ActionType.DONE -> { runWorker<DoneGoalWorker>(context, itemId, id) }
            ActionType.SKIP -> { runWorker<SkipGoalWorker>(context, itemId, id) }
            null -> Log.w("DailyHandler", "Unknown action: $action")
            ActionType.DELAY -> Log.w("DailyHandler", "Unknown action: $action")
        }
    }

}