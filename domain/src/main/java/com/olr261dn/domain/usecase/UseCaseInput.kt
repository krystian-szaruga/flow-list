package com.olr261dn.domain.usecase

interface UseCaseInput<T> {
    suspend fun execute(param: T)
}