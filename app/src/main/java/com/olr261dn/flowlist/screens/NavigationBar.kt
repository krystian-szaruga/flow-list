package com.olr261dn.flowlist.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.olr261dn.flowlist.navigation.NavigateToScreen
import com.olr261dn.flowlist.navigation.Screens
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.nonScaledSp

@Composable
internal fun NavigationBar(
    navigateToScreen: NavigateToScreen,
    initialScrollIndex: Int = 0,
) {
    val listState = rememberLazyListState()
    val currentBackStackEntry by navigateToScreen.navController.currentBackStackEntryAsState()
    val currentScreen = currentBackStackEntry?.destination?.route ?: ""

    LaunchedEffect(initialScrollIndex) {
        listState.scrollToItem(initialScrollIndex)
    }

    BottomAppBar(
        containerColor = customTheme().primary,
        contentColor = customTheme().onPrimary
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .weight(1f),
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            state = listState
        ) {
            item {
                NavigationBarItem(
                    painter = painterResource(R.drawable.round_calendar_today_24),
                    description = stringResource(id = R.string.daily_tasks_navigation),
                    isSelected = currentScreen == Screens.DailyHabits.name
                ) {
                    navigateToScreen.navigateAndClearBackStack(Screens.DailyHabits.name)
                }
            }
            item {
                NavigationBarItem(
                    painter = painterResource(R.drawable.round_assignment_24),
                    description = stringResource(id = R.string.tasks_navigation),
                    isSelected = currentScreen == Screens.Tasks.name
                ) {
                    navigateToScreen.navigateAndClearBackStack(Screens.Tasks.name)
                }
            }
            item {
                NavigationBarItem(
                    painter = painterResource(R.drawable.round_flag_24),
                    description = stringResource(id = R.string.goals_navigation),
                    isSelected = currentScreen == Screens.Goals.name
                ) {
                    navigateToScreen.navigateAndClearBackStack(Screens.Goals.name)
                }
            }
//                item {
//                    NavigationBarItem(
//                        painter = painterResource(R.drawable.round_settings_24),
//                        description = stringResource(id = R.string.settings_navigation),
//                        isSelected = currentScreen == Screens.Settings.name
//                    ) {
//                        navigateToScreen.navigateAndClearBackStack(Screens.Settings.name)
//                    }
//                }
        }
    }
}

@Composable
private fun NavigationBarItem(
    painter: Painter,
    description: String,
    isSelected: Boolean = false, // Add this parameter
    navigateOnClick: () -> Unit,
) {
    Card(
        shape = RectangleShape,
        modifier = Modifier.wrapContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
//            contentColor = customTheme().onPrimary
        ),
        onClick = { navigateOnClick.invoke() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(34.dp),
                painter = painter,
                contentDescription = description
            )
            Text(
                text = description,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 18.nonScaledSp
            )
            if (isSelected) {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .width(6.dp)
                        .height(6.dp)
                        .background(
                            color = customTheme().onPrimary.copy(0.7f),
                            shape = CircleShape
                        )
                )
            }
            Spacer(modifier = Modifier.width(60.dp))
        }
    }
}