package com.olr261dn.domain.notification

interface BootCoordinator {
    suspend fun run(): Boolean
}