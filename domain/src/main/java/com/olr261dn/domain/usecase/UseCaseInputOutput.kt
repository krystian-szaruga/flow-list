package com.olr261dn.domain.usecase

import kotlinx.coroutines.flow.Flow

interface UseCaseInputOutput<T,R> {
    fun execute(param: T): Flow<R>
}