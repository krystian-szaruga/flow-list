package com.olr261dn.viewmodel

import androidx.lifecycle.viewModelScope
import com.olr261dn.domain.model.Schedulable
import com.olr261dn.domain.usecase.BaseItemActions
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

abstract class ItemViewModel<T: Schedulable> (private val actions: BaseItemActions<T>):
    BaseViewModel<T>() {

    override val items: StateFlow<List<T>> = actions.get.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    override fun addItem(item: T) {
        launch(
            action = { actions.add.execute(item) }
        )
    }

    override fun cancelDelay(item: T) {
        launch(
            action = { actions.update.execute(item) }
        )
    }

    override fun updateItem(item: T) {
        launch(
            action = { actions.update.execute(item) }
        )
    }

    override fun removeItems(items: List<T>) {
        launch(
            action = { actions.remove.execute(items.map { it.id }) }
        )
    }
}