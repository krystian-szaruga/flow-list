package com.olr261dn.data.repository

import com.olr261dn.data.database.dao.DailyTaskDao
import com.olr261dn.data.mapper.toDomain
import com.olr261dn.data.mapper.toEntity
import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.domain.repository.DailyRoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

internal class DailyRoutineRepositoryImpl @Inject constructor(
    private val dailyTaskDao: DailyTaskDao
): DailyRoutineRepository {
    override suspend fun insert(param: RecurringTask) =
        dailyTaskDao.insert(param.toEntity())

    override suspend fun update(param: RecurringTask) {
        dailyTaskDao.update(param.toEntity())
    }

    override fun getAll(): Flow<List<RecurringTask>> {
        return dailyTaskDao.getAll().map { list -> list.map { it.toDomain() }}
    }

    override fun getById(id: UUID): Flow<RecurringTask?> =
        dailyTaskDao.getById(id).map { it?.toDomain() }

    override suspend fun delete(param: List<UUID>) {
        dailyTaskDao.delete(param)
    }

    override fun getIds(): Flow<List<UUID>> = dailyTaskDao.getIds()
}