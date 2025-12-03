package com.olr261dn.ui.compose.internal.utils

import androidx.annotation.StringRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.nonScaledSp

@Composable
internal fun ConfirmDeleteDialog(
    selectedCount: Int,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val showDialog = remember { mutableStateOf(true) }
    val onDismissUpdated = {
        showDialog.value = false
        onDismiss()
    }
    if (showDialog.value) {
        val context = LocalContext.current
        val confirmationText = remember(selectedCount) {
            context.resources.getQuantityString(
                R.plurals.removalConfirmationText,
                selectedCount
            )
        }
        AlertDialog(
            tonalElevation = 5.dp,
            onDismissRequest = { onDismissUpdated() },
            shape = RoundedCornerShape(15.dp),
            containerColor = customTheme().container,
            textContentColor = customTheme().onContainer,
            titleContentColor = customTheme().onContainer,
            iconContentColor = customTheme().onContainer,
            title = { TextLayout(R.string.removalConfirmationTitle, 30.nonScaledSp) },
            text = { TextLayout(confirmationText, 25.nonScaledSp) },
            confirmButton = {
                TextButtonLayout(R.string.ok) {
                    onConfirm()
                    onDismissUpdated()
                }
            },
            dismissButton = { TextButtonLayout(R.string.cancel) { onDismissUpdated() } }
        )
    }
}

@Composable
private fun TextButtonLayout(
    value: Int,
    onClick: () -> Unit,
) {
    TextButton(
        colors = ButtonDefaults.buttonColors(
            containerColor = customTheme().container,
            contentColor = customTheme().onContainer
        ),
        onClick = { onClick.invoke() }
    ) {
        TextLayout(value, 20.nonScaledSp)
    }
}


@Composable
private fun TextLayout(
    @StringRes value: Int,
    size: TextUnit
) {
    Text(
        text = stringResource(value),
        fontSize = size
    )
}

@Composable
private fun TextLayout(
    value: String,
    size: TextUnit
) {
    Text(
        text = value,
        fontSize = size
    )
}