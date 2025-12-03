package com.olr261dn.ui.compose.common
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.olr261dn.domain.model.Schedulable
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.nonScaledSp

@Composable
fun <T : Schedulable> DropdownSelector(
    items: List<T>,
    selectedItem: T?,
    isExpanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onItemSelected: (T) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpandChange(!isExpanded) },
        colors = CardDefaults.cardColors(
            containerColor = customTheme().surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.select_task),
                        fontSize = 12.nonScaledSp,
                        color = customTheme().onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = getTitle(selectedItem),
                        fontSize = 18.nonScaledSp,
                        fontWeight = FontWeight.SemiBold,
                        color = customTheme().onSurface
                    )
                }
                Icon(
                    imageVector = if (isExpanded)
                        Icons.Default.KeyboardArrowUp
                    else
                        Icons.Default.KeyboardArrowDown,
                    contentDescription = stringResource(
                        if (isExpanded) R.string.collapse else R.string.expand
                    ),
                    tint = customTheme().onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = customTheme().onSurface.copy(alpha = 0.1f)
                    )

                    items.forEach { item ->
                        DropdownItem(
                            item = item,
                            isSelected = item.id == selectedItem?.id,
                            onClick = { onItemSelected(item) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun <T: Schedulable> getTitle(item: T?): String {
    return item?.let {
        val disabled = stringResource(if (it.isDisabled) R.string.disabled else R.string.active)
        "${it.title} (${disabled})"
    } ?: stringResource(R.string.choose_task)
}


@Composable
private fun <T : Schedulable> DropdownItem(
    item: T,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(
                color = if (isSelected)
                    customTheme().primary.copy(alpha = 0.1f)
                else
                    Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.title,
            fontSize = 16.nonScaledSp,
            color = if (isSelected) customTheme().primary else customTheme().onSurface,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
        if (isSelected) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(R.string.selected_description),
                tint = customTheme().primary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
