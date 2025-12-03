package com.olr261dn.domain.usecase

import com.olr261dn.domain.model.Schedulable
import java.util.UUID

abstract class BaseItemActions<T: Schedulable> {
    abstract val add: UseCaseInput<T>
    abstract val get: UseCaseReturnOnly<List<T>>
    abstract val getById: UseCaseInputOutput<UUID, T?>
    abstract val remove: UseCaseInput<List<UUID>>
    abstract val update: UseCaseInput<T>
}