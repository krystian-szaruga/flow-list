package com.olr261dn.domain.notification

interface NotificationManagement {
    fun showNotification(
        id: Long,
        message: String,
        subtitle: String?,
        actions: List<NotificationAction> = emptyList(),
        deleteAction: NotificationAction
    )
    fun cancelNotification(id: Long)
}