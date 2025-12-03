package com.olr261dn.ui.compose.internal.screenBody.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.olr261dn.domain.model.Schedulable

@Composable
internal fun<T: Schedulable> LazyElements(
    items: List<T>,
    selectedItems: List<T>,
    onSelect: (T) -> Unit,
    onDetail: (T) -> Unit,
    content: @Composable (T, Boolean, (T) -> Unit, (T) -> Unit) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items, key = { it.id }) { item ->
            val isSelected = selectedItems.contains(item)
            content(
                item,
                isSelected,
                { onDetail(it) },
                { onSelect(it) }
            )
        }
    }
}
