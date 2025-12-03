package com.olr261dn.project.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.olr261dn.domain.model.Task
import com.olr261dn.resources.R
import com.olr261dn.ui.compose.common.OverlayDialog
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.viewmodel.TaskItemViewModel

@Composable
internal fun LinkTaskPopup(titles: Set<String>, onDismiss: () -> Unit, onAdd: (Task) -> Unit) {
    val taskViewModel: TaskItemViewModel = hiltViewModel()
    val tasks by taskViewModel.tasks.collectAsState()
    val uniqueTasks = tasks.distinctBy { it.title }.filterNot { it.title in titles }

    OverlayDialog(onDismiss = onDismiss) {
        Button(
            onClick = { onDismiss() },
            colors = ButtonDefaults.buttonColors(
                containerColor = customTheme().surface,
                contentColor = customTheme().onSurface
            ),
            modifier = Modifier.wrapContentSize(),
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.cancel)
            )
        }
        Spacer(Modifier.height(10.dp))
        uniqueTasks.forEach { task ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .padding(10.dp)
                    .clickable { onAdd(task) },
                elevation = CardDefaults.cardElevation(8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = customTheme().secondary,
                    contentColor = customTheme().onSecondary
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = task.title,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
