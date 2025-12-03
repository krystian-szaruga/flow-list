package com.olr261dn.recurring

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.olr261dn.domain.enums.RecurringScheduleType
import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.nonScaledSp
import com.olr261dn.viewmodel.StatsViewModel
import java.text.DateFormat
import java.time.DayOfWeek
import java.util.Date

@Composable
internal fun DetailsContent(item: RecurringTask) {
    val statsViewModel = hiltViewModel<StatsViewModel>()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = customTheme().secondary,
            contentColor = customTheme().onSecondary
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                SectionTitle(
                    title = item.title,
                    fontSize = 50.nonScaledSp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SectionTitle(stringResource(R.string.description))
                    SectionBody(
                        value = item.description,
                        expandable = true
                    )
                }
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SectionTitle(stringResource(R.string.scheduled))
                    SectionBody(
                        DateFormat.getTimeInstance(DateFormat.SHORT)
                            .format(Date(item.scheduledTime))
                    )
                    TextLayout(
                        isActive = !item.isDisabled,
                        scheduleType = item.scheduleType,
                        customDays = item.customDays
                    )
                }
            }
            item {
                LastDoneIndicator(statsViewModel)
            }
        }
    }

}

@Composable
private fun SectionTitle(
    title: String,
    fontSize: TextUnit = 30.nonScaledSp,
    fontWeight: FontWeight = FontWeight.Bold
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(color = customTheme().surface),
        contentAlignment = Alignment.Center
    ) {
        SelectionContainer {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                fontSize = fontSize,
                fontWeight = fontWeight,
                color = customTheme().onSurface
            )
        }
    }
}

@Composable
private fun SectionBody(value: String, expandable: Boolean = false) {
    val expanded = remember { mutableStateOf(false) }
    val clickableModifier = if (expandable) {
        Modifier.clickable { expanded.value = !expanded.value }
    } else {
        Modifier
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(
                width = 0.5.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(10.dp)
            )
            .background(Color.Transparent)
            .then(clickableModifier)
            .padding(8.dp)
            .animateContentSize(),
        contentAlignment = Alignment.Center
    ) {
        SelectionContainer {
            Text(
                text = value,
                maxLines = if (expandable && !expanded.value) 5 else Int.MAX_VALUE,
                fontSize = 20.nonScaledSp,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun TextLayout(
    isActive: Boolean,
    scheduleType: RecurringScheduleType,
    customDays: Set<DayOfWeek>
) {
    val theme = customTheme()
    val color = if (isActive) theme.greenOnSecondary else theme.redOnSecondary
    val value = if (isActive) stringResource(R.string.active) else stringResource(R.string.disabled)

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = value,
            fontSize = 20.nonScaledSp,
            color = color
        )

        Text(
            text = if (scheduleType == RecurringScheduleType.CUSTOM_DAYS)
                customDays.toShortLabel()
            else
                stringResource(scheduleType.labelRes),
            fontSize = 18.nonScaledSp,
            color = color
        )
    }
}


@Composable
private fun LastDoneIndicator(viewModel: StatsViewModel) {
    val lastStats by viewModel.dailyTaskLastStatus().collectAsState(initial = null)

    lastStats?.let { stat ->
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                contentColor = customTheme().onTertiary,
                color = if (stat.isCompleted) Color.Green.copy(alpha = 0.2f) else
                    Color.Red.copy(alpha = 0.2f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(R.string.status), fontSize = 17.nonScaledSp)
                    Spacer(Modifier.width(4.dp))
                    Icon(
                        imageVector = if (stat.isCompleted)
                            Icons.Default.CheckCircle
                        else
                            Icons.Default.Clear,
                        contentDescription = null,
                        tint = customTheme().getCompletedColor(stat.isCompleted),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}
