package com.olr261dn.domain.usecase

data class BiometricPreferencesActions (
    val getBiometricStatus: UseCaseReturnOnly<Boolean>,
    val setBiometricStatus: UseCaseInput<Boolean>
)