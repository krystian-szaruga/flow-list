package com.olr261dn.domain.repository

import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface BaseRepository {
    fun getIds(): Flow<List<UUID>>
}