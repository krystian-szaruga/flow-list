package com.olr261dn.domain.usecase

interface UseCaseReturnOnlySuspend<T> {
    suspend fun execute(): T
}