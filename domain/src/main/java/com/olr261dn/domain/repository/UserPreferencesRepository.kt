package com.olr261dn.domain.repository

import com.olr261dn.domain.model.NavbarPosition
import com.olr261dn.domain.model.QuickActionPosition
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    suspend fun setDefaultScreen(screen: String)
    fun getDefaultScreen(): Flow<String?>
    suspend fun setCustomScreen(screen: String?)
    suspend fun setNavBarPosition(position: NavbarPosition)
    fun getNavBarPosition(): Flow<NavbarPosition>
    suspend fun setFabPosition(position: QuickActionPosition)
    fun getFabPosition(): Flow<QuickActionPosition>
    fun getRecurringTaskDelay(): Flow<Long>
    fun getTaskDelay(): Flow<Long>
    suspend fun setRecurringTaskDelay(value: Long)
    suspend fun setTaskDelay(value: Long)
    fun getBiometricStatus(): Flow<Boolean>
    suspend fun setBiometricStatus(status: Boolean)
}