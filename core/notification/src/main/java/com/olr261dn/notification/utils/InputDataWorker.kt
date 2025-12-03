package com.olr261dn.notification.utils

import java.util.UUID

internal data class InputDataWorker(val id: Long, val itemId: UUID) {
    constructor(id: Long,itemId: String) : this(id, UUID.fromString(itemId))
}
