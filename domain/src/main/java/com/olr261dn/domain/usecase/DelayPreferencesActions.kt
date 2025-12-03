package com.olr261dn.domain.usecase

data class DelayPreferencesActions(
    val getRecurringTaskDelay: UseCaseReturnOnly<Long>,
    val getTaskDelay: UseCaseReturnOnly<Long>,
    val setRecurringTaskDelay: UseCaseInput<Long>,
    val setTaskDelay: UseCaseInput<Long>
)
