package com.olr261dn.ui.compose.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.olr261dn.ui.compose.constants.Const

@Composable
fun TitleField(isError: Boolean, label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        isError = isError,
        value = value,
        onValueChange = onValueChange,
        label = { Text(text=label, textAlign = TextAlign.Center) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = Const.textFieldColors()
    )
}
