package com.olr261dn.domain.viewmodel

import kotlinx.coroutines.flow.StateFlow


interface BaseItemViewModel<T>{
    val items: StateFlow<List<T>>
    fun addItem(item: T)
    fun cancelDelay(item: T)
    fun updateItem(item: T)
    fun sortedItems(items: List<T>): List<T>
    fun removeItems(items: List<T>)
}
