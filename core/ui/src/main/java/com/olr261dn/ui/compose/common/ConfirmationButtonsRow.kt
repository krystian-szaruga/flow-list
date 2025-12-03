package com.olr261dn.ui.compose.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.nonScaledSp


@Composable
fun ConfirmationButtonsRow(
    dismissLabel: String = stringResource(R.string.cancel),
    confirmLabel: String = stringResource(R.string.save),
    rowArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = rowArrangement
    ) {
        ConfirmationButtons(
            dismissLabel=dismissLabel,
            confirmLabel=confirmLabel,
            onDismiss = onDismiss,
            onConfirm = onConfirm
        )
    }
}


@Composable
private fun ConfirmationButtons(
    dismissLabel: String = stringResource(R.string.cancel),
    confirmLabel: String = stringResource(R.string.save),
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    ButtonLayout(dismissLabel) { onDismiss() }
    ButtonLayout(confirmLabel) { onConfirm() }
}

@Composable
private fun ButtonLayout(
    confirmLabel: String,
    onConfirm: () -> Unit
) {
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = customTheme().background,
        contentColor = customTheme().onBackground
    )
    Button(onClick = onConfirm, colors = buttonColors) {
        Text(
            text = confirmLabel,
            fontSize = 16.nonScaledSp
        )
    }
}
