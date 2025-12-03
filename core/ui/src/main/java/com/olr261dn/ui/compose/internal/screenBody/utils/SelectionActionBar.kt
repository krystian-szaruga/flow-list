package com.olr261dn.ui.compose.internal.screenBody.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.olr261dn.resources.R
import com.olr261dn.ui.compose.internal.utils.ConfirmDeleteDialog
import com.olr261dn.ui.compose.internal.utils.IconButtonLayout

@Composable
internal fun SelectionActionBar(
    selectedSize: Int,
    size: Int,
    onSelectAllToggle: () -> Unit,
    onCancel: () -> Unit,
    onDelete: () -> Unit
) {
    val removeSelected = remember { mutableStateOf(false) }
    if (removeSelected.value) {
        ConfirmDeleteDialog(
            selectedCount = selectedSize,
            onDismiss = { removeSelected.value = false },
            onConfirm = {
                onDelete()
                onCancel()
            }
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButtonLayout(value = R.string.cancel, icon = Icons.Default.Close) { onCancel() }
            Text(
                modifier = Modifier.clickable { onSelectAllToggle() },
                text = "$selectedSize / $size",
                style = MaterialTheme.typography.bodyMedium
            )
            IconButtonLayout(value = R.string.delete, icon = Icons.Default.Delete) {
                removeSelected.value = true
            }
        }
    }
}

