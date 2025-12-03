package com.olr261dn.domain.notification

import java.util.UUID

interface DisplayerCoordinator {
    suspend fun displayNotification(id: Long, itemId: UUID): Boolean
}