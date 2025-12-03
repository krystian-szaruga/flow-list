package com.olr261dn.ui.compose.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.olr261dn.resources.R
import com.olr261dn.ui.compose.constants.Const

@Composable
fun DescriptionField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = stringResource(R.string.description_placeholder)) },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        textStyle = LocalTextStyle.current.copy(
            lineHeight = 20.sp
        ),
        maxLines = 5,
        singleLine = false,
        colors = Const.textFieldColors()
    )
}