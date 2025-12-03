package com.olr261dn.data.database.dao

import kotlinx.coroutines.flow.Flow

internal interface BaseDao<T, ID> {
    suspend fun insert(item: T)

    suspend fun update(item: T)

    fun getAll(): Flow<List<T>>

    fun getById(id: ID): Flow<T?>

    fun getIds(): Flow<List<ID>>

    suspend fun delete(ids: List<ID>)
}