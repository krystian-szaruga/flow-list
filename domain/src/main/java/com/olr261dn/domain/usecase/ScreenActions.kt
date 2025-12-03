package com.olr261dn.domain.usecase

data class ScreenActions(
    val get: UseCaseReturnOnly<String?>,
    val setDefault: UseCaseInput<String>
)