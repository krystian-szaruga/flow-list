package com.olr261dn.domain.notification

import java.util.UUID

interface ActionCoordinator {
    suspend fun run(id: Long, itemId: UUID): Boolean
}