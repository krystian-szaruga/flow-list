package com.olr261dn.recurring

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.olr261dn.domain.enums.RecurringScheduleType
import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.ui.compose.common.ItemCardLayout
import com.olr261dn.ui.compose.common.TaskDelayedCard
import com.olr261dn.ui.theme.ThemeColor
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.formatTime
import com.olr261dn.ui.utils.nonScaledSp
import com.olr261dn.viewmodel.DailyNotificationActionsViewModel
import com.olr261dn.viewmodel.ReminderViewModel
import com.olr261dn.viewmodel.RecurringTaskItemViewModel
import java.time.DayOfWeek

@Composable
fun ItemDisplayer(
    viewModel: RecurringTaskItemViewModel,
    item: RecurringTask,
    isSelected: Boolean,
    onClick: (RecurringTask) -> Unit,
    onLongClick: (RecurringTask) -> Unit
) {
    val theme = customTheme()
    val reminderViewModel = hiltViewModel<ReminderViewModel>()
    ItemCardLayout(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick(item) },
                    onLongPress = { onLongClick(item) }
                )
            }
            .alpha(if (item.isDisabled) 0.5f else 1f),
        isSelected = isSelected
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TaskTitleCard(title = item.title, isSelected = isSelected, theme = theme)
            TaskScheduleCard(
                scheduledTime = item.scheduledTime,
                scheduleType = item.scheduleType,
                customDays = item.customDays,
                isSelected = isSelected,
                theme = theme
            )
            if (item.delayedTime != null) {
                val actionViewModel = hiltViewModel<DailyNotificationActionsViewModel>()
                TaskDelayedCard(
                    type = ReminderType.DAILY,
                    item = item,
                    isSelected = isSelected,
                    reminderViewModel = reminderViewModel,
                    actionViewModel = actionViewModel
                ) {
                    viewModel.cancelDelay(item.copy(delayedTime = null))
                }
            }
        }
    }
}

@Composable
private fun TaskTitleCard(
    title: String,
    isSelected: Boolean,
    theme: ThemeColor
) {
    Card(
        modifier = Modifier.fillMaxWidth(0.98f),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = theme.getSurface(isSelected),
            contentColor = theme.getOnSurface(isSelected)
        ),
    ) {
        Text(
            text = title.uppercase(),
            textAlign = TextAlign.Center,
            fontSize = 40.nonScaledSp,
            color = theme.getOnSurface(isSelected),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp)
        )
    }
}

@Composable
private fun TaskScheduleCard(
    scheduledTime: Long,
    scheduleType: RecurringScheduleType,
    customDays: Set<DayOfWeek>,
    isSelected: Boolean,
    theme: ThemeColor
) {
    val label = when (scheduleType) {
        RecurringScheduleType.CUSTOM_DAYS -> customDays.toShortLabel()
        else -> stringResource(scheduleType.labelRes)
    }
    Card(
        modifier = Modifier.fillMaxWidth(0.99f),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = theme.getSurface(isSelected),
            contentColor = theme.getOnSurface(isSelected)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = formatTime(scheduledTime, LocalContext.current),
                fontSize = 20.nonScaledSp,
                color = theme.getOnSurface(isSelected),
                modifier = Modifier.padding(horizontal = 2.dp, vertical = 6.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                fontSize = 20.nonScaledSp,
                color = theme.getOnSurface(isSelected),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 2.dp, vertical = 6.dp)
            )
        }
    }
}


