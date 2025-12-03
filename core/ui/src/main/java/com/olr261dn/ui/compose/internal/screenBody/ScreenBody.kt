package com.olr261dn.ui.compose.internal.screenBody

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.olr261dn.domain.model.Schedulable
import com.olr261dn.domain.viewmodel.BaseItemViewModel
import com.olr261dn.ui.compose.internal.screenBody.utils.LazyElements
import com.olr261dn.ui.compose.internal.screenBody.utils.Placeholder
import com.olr261dn.ui.compose.internal.screenBody.utils.SelectionActionBar

@Composable
internal fun <T : Schedulable, VM: BaseItemViewModel<T>> ScreenBody(
    viewModel: VM,
    content: @Composable (T, Boolean, onClick: (T) -> Unit, onLongClick: (T) -> Unit) -> Unit,
    onDelete: (List<T>) -> Unit,
    onDetail: (T) -> Unit
) {
    var selectedItems by remember { mutableStateOf<List<T>>(emptyList()) }
    val selectPress: (T) -> Unit = { task ->
        selectedItems =
            if (selectedItems.contains(task)) selectedItems - task
            else selectedItems + task
    }
    val detailPress: (T) -> Unit = { item ->
        if (selectedItems.isEmpty()) onDetail(item)
        else selectPress(item)
    }
    val items by viewModel.items.collectAsStateWithLifecycle(emptyList())
    val sortedItems = remember(items) { viewModel.sortedItems(items) }
    val itemSize = items.size
    val toggleSelectAll = {
        selectedItems =
            if (selectedItems.size == itemSize) emptyList()
            else items
    }

    Column {
        if (selectedItems.isNotEmpty()) {
            SelectionActionBar(
                selectedSize = selectedItems.size,
                size = itemSize,
                onSelectAllToggle = toggleSelectAll,
                onCancel = { selectedItems = emptyList()},
                onDelete = { onDelete(selectedItems) }
            )
        }
        if (sortedItems.isEmpty()) {
            Placeholder()
        } else {
            LazyElements(
                sortedItems,
                selectedItems = selectedItems,
                onSelect = selectPress,
                onDetail = detailPress
            ) { item, isSelected, onSelect, onDetail ->
                content(item, isSelected, onSelect, onDetail)
            }
        }
    }
}

