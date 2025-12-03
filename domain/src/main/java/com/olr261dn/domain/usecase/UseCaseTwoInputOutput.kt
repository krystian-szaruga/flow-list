package com.olr261dn.domain.usecase

interface UseCaseTwoInputOutput<P1,P2, T> {
    suspend fun execute(param1: P1, param2: P2): T
}