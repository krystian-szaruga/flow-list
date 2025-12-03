package com.olr261dn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olr261dn.domain.model.NavbarPosition
import com.olr261dn.domain.model.QuickActionPosition
import com.olr261dn.domain.usecase.BiometricPreferencesActions
import com.olr261dn.domain.usecase.DelayPreferencesActions
import com.olr261dn.domain.usecase.PositionActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val positionActions: PositionActions,
    private val delayPreferencesActions: DelayPreferencesActions,
    private val biometricPreferencesActions: BiometricPreferencesActions
) : ViewModel() {
    val navBarPosition: StateFlow<NavbarPosition?> = positionActions.getNavbar.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val quickActionPosition: StateFlow<QuickActionPosition?> = positionActions.getFab.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val recurringTaskDelay: StateFlow<Long?> = delayPreferencesActions.getRecurringTaskDelay.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val taskDelay: StateFlow<Long?> = delayPreferencesActions.getTaskDelay.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val biometricStatus: StateFlow<Boolean?> = biometricPreferencesActions.getBiometricStatus.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    fun setBiometricStatus(status: Boolean) {
        viewModelScope.launch {
            biometricPreferencesActions.setBiometricStatus.execute(status)
        }
    }

    fun setNavbarPosition(position: NavbarPosition) {
        viewModelScope.launch {
            positionActions.setNavbar.execute(position)
        }
    }

    fun setFabPosition(position: QuickActionPosition) {
        viewModelScope.launch {
            positionActions.setFab.execute(position)
        }
    }

    fun setRecurringTaskDelay(value: Long) {
        viewModelScope.launch {
            delayPreferencesActions.setRecurringTaskDelay.execute(value)
        }
    }

    fun setTaskDelay(value: Long) {
        viewModelScope.launch {
            delayPreferencesActions.setTaskDelay.execute(value)
        }
    }
}