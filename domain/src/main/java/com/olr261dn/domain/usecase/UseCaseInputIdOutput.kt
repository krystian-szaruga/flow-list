package com.olr261dn.domain.usecase

interface UseCaseInputIdOutput<T,R> {
    suspend fun execute(param: T): R
}