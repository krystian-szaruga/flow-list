package com.olr261dn.impl.notification.displayer

import android.Manifest
import androidx.annotation.RequiresPermission
import com.olr261dn.domain.model.Schedulable
import com.olr261dn.domain.notification.NotificationAction
import com.olr261dn.domain.notification.NotificationManagement
import com.olr261dn.domain.usecase.BaseItemActions
import kotlinx.coroutines.flow.firstOrNull
import java.util.UUID

internal abstract class BaseNotificationCoordinator<T: Schedulable>(
    private val action: BaseItemActions<T>,
    private val displayer: NotificationManagement,
) {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    suspend fun displayNotification(
        id: Long,
        itemId: UUID,
    ): Boolean {
        val item = getById(itemId) ?: return false
        displayer.cancelNotification(id)
        displayer.showNotification(
            id, item.title, getSubTitle(item), getActions(id, item), dismissAction(id, itemId)
        )
        return true
    }

    private suspend fun getById(id: UUID): T? = action.getById.execute(id).firstOrNull()

    protected abstract fun getActions(id: Long, item: T): List<NotificationAction>

    protected abstract fun dismissAction(id: Long, itemId: UUID): NotificationAction

    protected open suspend fun getSubTitle(item: T): String? = null

}
