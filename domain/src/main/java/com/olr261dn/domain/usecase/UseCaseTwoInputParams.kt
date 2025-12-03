package com.olr261dn.domain.usecase

interface UseCaseTwoInputParams<T, R> {
    suspend fun execute(param1: T, param2: R)
}