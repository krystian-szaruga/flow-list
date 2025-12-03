package com.olr261dn.domain.repository

import com.olr261dn.domain.model.Schedulable
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ItemRepository<T: Schedulable>: BaseRepository {
    suspend fun insert(param: T)
    suspend fun update(param: T)
    fun getAll(): Flow<List<T>>
    fun getById(id: UUID): Flow<T?>
    suspend fun delete(param: List<UUID>)
}