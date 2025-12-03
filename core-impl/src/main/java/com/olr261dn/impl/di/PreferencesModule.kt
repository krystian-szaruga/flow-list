package com.olr261dn.impl.di

import com.olr261dn.domain.repository.UserPreferencesRepository
import com.olr261dn.domain.usecase.BiometricPreferencesActions
import com.olr261dn.domain.usecase.DelayPreferencesActions
import com.olr261dn.domain.usecase.PositionActions
import com.olr261dn.domain.usecase.ScreenActions
import com.olr261dn.impl.delay.GetRecurringTaskDelay
import com.olr261dn.impl.delay.GetTaskDelay
import com.olr261dn.impl.delay.SetRecurringTaskDelay
import com.olr261dn.impl.delay.SetTaskDelay
import com.olr261dn.impl.position.GetFabPosition
import com.olr261dn.impl.position.GetNavBarPosition
import com.olr261dn.impl.position.SetFabPosition
import com.olr261dn.impl.position.SetNavBarPosition
import com.olr261dn.impl.screen.GetDefaultScreen
import com.olr261dn.impl.screen.SetDefaultScreen
import com.olr261dn.impl.security.GetBiometricStatus
import com.olr261dn.impl.security.SetBiometricStatus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object PreferencesModule {
    @Provides
    @Singleton
    fun provideScreenActions(repository: UserPreferencesRepository): ScreenActions =
        ScreenActions(
            get = GetDefaultScreen(repository),
            setDefault = SetDefaultScreen(repository)
        )

    @Provides
    @Singleton
    fun providePositionActions(repository: UserPreferencesRepository): PositionActions =
        PositionActions(
            getNavbar = GetNavBarPosition(repository),
            setNavbar = SetNavBarPosition(repository),
            getFab = GetFabPosition(repository),
            setFab = SetFabPosition(repository)
        )

    @Provides
    @Singleton
    fun provideBiometricPreferencesActions(repository: UserPreferencesRepository): BiometricPreferencesActions =
        BiometricPreferencesActions(
            getBiometricStatus = GetBiometricStatus(repository),
            setBiometricStatus = SetBiometricStatus(repository)
        )

    @Provides
    @Singleton
    fun provideDelayPreferencesActions(
        repository: UserPreferencesRepository
    ): DelayPreferencesActions {
        return DelayPreferencesActions(
            getRecurringTaskDelay = GetRecurringTaskDelay(repository),
            getTaskDelay = GetTaskDelay(repository),
            setRecurringTaskDelay = SetRecurringTaskDelay(repository),
            setTaskDelay = SetTaskDelay(repository)
        )
    }
}