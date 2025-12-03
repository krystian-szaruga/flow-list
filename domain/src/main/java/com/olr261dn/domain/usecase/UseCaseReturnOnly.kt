package com.olr261dn.domain.usecase

import kotlinx.coroutines.flow.Flow

interface UseCaseReturnOnly<T> {
    fun execute(): Flow<T>
}