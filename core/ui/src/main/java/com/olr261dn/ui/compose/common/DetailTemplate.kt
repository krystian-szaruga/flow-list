package com.olr261dn.ui.compose.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.olr261dn.resources.R
import com.olr261dn.ui.compose.internal.utils.ConfirmDeleteDialog
import com.olr261dn.ui.compose.internal.utils.IconButtonLayout

@Composable
fun DetailTemplate(
    title: String,
    onBackPress: () -> Unit,
    onRemoveConfirm: () -> Unit,
    popup: @Composable (() -> Unit) -> Unit,
    detailsBody: @Composable () -> Unit
) {
    val editItem = rememberSaveable { mutableStateOf(false) }
    val removeItem = rememberSaveable { mutableStateOf(false) }
    val onRemovePress: (Boolean) -> Unit = { removeItem.value = it }
    val onEditPress: (Boolean) -> Unit = { editItem.value = it }

    ScaffoldWithTopBar(
        title = title,
        onBackPress = { onBackPress() },
        actions = {
            IconButtonLayout(
                value = R.string.edit,
                icon = Icons.Default.Edit
            ) {
                onEditPress(!editItem.value)
            }
            IconButtonLayout(
                value = R.string.delete,
                icon = Icons.Default.Delete
            ) {
                onRemovePress(true)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(10.dp)
                .fillMaxSize()
                .consumeWindowInsets(innerPadding)
        ) {
            if (editItem.value) popup { onEditPress(false) }
            if (removeItem.value) ConfirmDelete(onDismiss = { onRemovePress(false) }) {
                onRemoveConfirm()
            }
            detailsBody()
        }
    }
}


@Composable
private fun ConfirmDelete(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    ConfirmDeleteDialog(
        selectedCount = 1,
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm () }
    )

}