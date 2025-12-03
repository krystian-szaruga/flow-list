package com.olr261dn.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.olr261dn.data.utils.dataStore
import com.olr261dn.domain.model.NavbarPosition
import com.olr261dn.domain.model.QuickActionPosition
import com.olr261dn.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


internal class UserPreferencesRepositoryImpl(context: Context): UserPreferencesRepository {
    private val dataStore = context.dataStore

    companion object {
        val DEFAULT_SCREEN_KEY = stringPreferencesKey("default_screen")
        val CUSTOM_SCREEN_KEY = stringPreferencesKey("custom_screen")
        val NAV_BAR_POSITION_KEY = stringPreferencesKey("nav_bar_position")
        val FAB_POSITION_KEY = stringPreferencesKey("fab_position")
        val RECURRING_TASK_DELAY_KEY = longPreferencesKey("recurring_task_delay")
        val TASK_DELAY_KEY = longPreferencesKey("task_delay")
        val BIOMETRIC_STATUS = booleanPreferencesKey("biometric_status")
    }

    override fun getRecurringTaskDelay(): Flow<Long> {
        return dataStore.data.map { preferences ->
            preferences[RECURRING_TASK_DELAY_KEY] ?: (10L * 60 * 1000)
        }
    }

    override fun getTaskDelay(): Flow<Long> {
        return dataStore.data.map { preferences ->
            preferences[TASK_DELAY_KEY] ?: (1L * 24 * 60 * 60 * 1000)
        }
    }

    override suspend fun setRecurringTaskDelay(value: Long) {
        dataStore.edit {
            it[RECURRING_TASK_DELAY_KEY] = value
        }
    }

    override suspend fun setTaskDelay(value: Long) {
        dataStore.edit {
            it[TASK_DELAY_KEY] = value
        }
    }

    override fun getBiometricStatus(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[BIOMETRIC_STATUS] ?: false
        }
    }

    override suspend fun setBiometricStatus(status: Boolean) {
        dataStore.edit {
            it[BIOMETRIC_STATUS] = status
        }
    }

    override suspend fun setDefaultScreen(screen: String) {
        dataStore.edit {
            it[DEFAULT_SCREEN_KEY] = screen
        }
    }

    override fun getDefaultScreen(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[CUSTOM_SCREEN_KEY] ?: preferences[DEFAULT_SCREEN_KEY]
        }
    }

    override suspend fun setCustomScreen(screen: String?) {
        dataStore.edit { pref ->
            screen?.let { pref[CUSTOM_SCREEN_KEY] = it } ?: pref.remove(CUSTOM_SCREEN_KEY)
        }
    }

    override suspend fun setNavBarPosition(position: NavbarPosition) {
        dataStore.edit {
            it[NAV_BAR_POSITION_KEY] = position.name
        }
    }

    override fun getNavBarPosition(): Flow<NavbarPosition> {
        return dataStore.data.map { preferences ->
            preferences[NAV_BAR_POSITION_KEY]?.let {
                NavbarPosition.valueOf(it)
            } ?: NavbarPosition.BOTTOM
        }
    }

    override suspend fun setFabPosition(position: QuickActionPosition) {
        dataStore.edit {
            it[FAB_POSITION_KEY] = position.name
        }
    }

    override fun getFabPosition(): Flow<QuickActionPosition> {
        return dataStore.data.map { preferences ->
            preferences[FAB_POSITION_KEY]?.let {
                QuickActionPosition.valueOf(it)
            } ?: QuickActionPosition.BOTTOM_RIGHT
        }
    }
}