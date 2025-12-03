package com.olr261dn.recurring.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.olr261dn.recurring.toShortLabel
import com.olr261dn.resources.R
import java.time.DayOfWeek

@Composable
internal fun CustomDaysDisplay(
    selectedDays: Set<DayOfWeek>,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = selectedDays.toShortLabel(),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )

        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = stringResource(R.string.select_days)
        )
    }
}
