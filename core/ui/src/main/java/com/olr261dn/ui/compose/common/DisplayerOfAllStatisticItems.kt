package com.olr261dn.ui.compose.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.formatDate
import com.olr261dn.ui.utils.nonScaledSp


@Composable
fun DisplayerOfAllStatisticItems(
    stats: List<CompletionHistory>?,
    onUpdateCompletion: (CompletionHistory) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            contentColor = customTheme().onSurface,
            containerColor = customTheme().surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            stats?.let {
                ItemStatsSection(
                    stats = stats,
                    onToggleCompletion = { stat ->
                        onUpdateCompletion(stat)
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun ItemStatsSection(
    stats: List<CompletionHistory>,
    onToggleCompletion: (CompletionHistory) -> Unit
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(R.string.stats_list_title),
            fontSize = 25.nonScaledSp,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        stats.sortedByDescending { it.markedAt }.forEach { stat ->
            StatisticItemRow(
                stat = stat,
                onToggle = { onToggleCompletion(stat.copy(isCompleted = !stat.isCompleted)) }
            )
        }
    }
}

@Composable
private fun StatisticItemRow(
    stat: CompletionHistory,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = formatDate(stat.markedAt, LocalContext.current),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        IconButton(onClick = onToggle) {
            Icon(
                imageVector = if (stat.isCompleted) Icons.Default.Check else Icons.Default.Close,
                contentDescription = stringResource(
                    if (stat.isCompleted) R.string.completed else R.string.not_completed
                ),
                tint = customTheme().getCompletedColor(stat.isCompleted)
            )
        }
    }
}
