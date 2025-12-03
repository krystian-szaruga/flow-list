package com.olr261dn.domain.viewmodel

import java.util.UUID

interface NotificationActions {
    fun markAsDone(id: Long, itemId: UUID)
    fun markAsSkip(id: Long, itemId: UUID)
}