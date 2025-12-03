package com.olr261dn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olr261dn.domain.notification.ActionCoordinator
import com.olr261dn.domain.viewmodel.NotificationActions
import kotlinx.coroutines.launch
import java.util.UUID

abstract class NotificationActionsViewModel(
    private val done: ActionCoordinator,
    private val skip: ActionCoordinator
) : ViewModel(), NotificationActions {

    override fun markAsDone(id: Long, itemId: UUID) {
        viewModelScope.launch {
            done.run(id, itemId)
        }
    }

    override fun markAsSkip(id: Long, itemId: UUID) {
        viewModelScope.launch {
            skip.run(id, itemId)
        }
    }
}