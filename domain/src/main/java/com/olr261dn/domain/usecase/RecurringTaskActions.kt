package com.olr261dn.domain.usecase

import com.olr261dn.domain.model.RecurringTask
import java.util.UUID

data class RecurringTaskActions(
    override val add: UseCaseInput<RecurringTask>,
    override val get: UseCaseReturnOnly<List<RecurringTask>>,
    override val getById: UseCaseInputOutput<UUID, RecurringTask?>,
    override val remove: UseCaseInput<List<UUID>>,
    override val update: UseCaseInput<RecurringTask>
): BaseItemActions<RecurringTask>()