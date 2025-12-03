package com.olr261dn.ui.compose.common

import android.text.format.DateUtils
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.model.Schedulable
import com.olr261dn.domain.viewmodel.NotificationActions
import com.olr261dn.domain.viewmodel.ReminderController
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.formatTime
import com.olr261dn.ui.utils.nonScaledSp

@Composable
fun <T: Schedulable>TaskDelayedCard(
    type: ReminderType,
    item: T,
    actionViewModel: NotificationActions,
    isSelected: Boolean,
    reminderViewModel: ReminderController,
    handleDelayCancel: () -> Unit,
) {
    val format = DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME or
            DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_NUMERIC_DATE
    val context = LocalContext.current
    val formattedTime = remember(item.delayedTime) {
        item.delayedTime?.let { formatTime(it, context, format) } ?: ""
    }

    val reminders = reminderViewModel.reminders.collectAsState()
    val id = reminders.value.firstOrNull {
        it.itemId == item.id && it.type == type
    }?.id ?: run {
        Log.w(
            "TaskDelayedCard",
            "Cannot find reminder for itemId: ${item.id}, type: $type"
        )
        -1L
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = customTheme().getSurface(isSelected),
            contentColor = customTheme().getOnSurface(isSelected)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formattedTime,
                fontSize = 14.nonScaledSp,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(R.string.mark_as_done),
                    modifier = Modifier
                        .size(26.dp)
                        .clickable {
                            actionViewModel.markAsDone(id, item.id)
                            handleDelayCancel()
                        }
                        .padding(4.dp)
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.mark_as_skip),
                    modifier = Modifier
                        .size(26.dp)
                        .clickable {
                            actionViewModel.markAsSkip(id, item.id)
                            handleDelayCancel()
                        }
                        .padding(4.dp)
                )
            }
        }
    }
}