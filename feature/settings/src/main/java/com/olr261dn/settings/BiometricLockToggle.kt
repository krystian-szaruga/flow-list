package com.olr261dn.settings

import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.ThemeColor
import com.olr261dn.ui.utils.nonScaledSp

@Composable
fun BiometricLockToggle(
    isEnabled: Boolean?,
    theme: ThemeColor,
    onChange: (Boolean) -> Unit
) {
    isEnabled?.let {
        val context = LocalContext.current
        val activity = context as FragmentActivity
        var showError by remember { mutableStateOf<String?>(null) }
        val biometricPrompt = remember {
            BiometricPrompt(
                activity,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        onChange(true)
                        Toast.makeText(
                            context,
                            context.getString(R.string.biometric_unlock_successful),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        showError = context.getString(R.string.biometric_unlock_error, errString)
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        showError = context.getString(R.string.biometric_unlock_failed)
                    }
                }
            )
        }
        val promptInfo = remember {
            BiometricPrompt.PromptInfo.Builder()
                .setTitle(context.getString(R.string.biometric_unlock_title))
                .setDescription(context.getString(R.string.biometric_unlock_description))
                .setNegativeButtonText(context.getString(R.string.biometric_unlock_cancel))
                .build()
        }
        LaunchedEffect(showError) {
            showError?.let { error ->
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                showError = null
            }
        }
        val handleToggle: (Boolean) -> Unit = { newValue ->
            if (newValue) {
                val biometricManager = BiometricManager.from(context)
                when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
                    BiometricManager.BIOMETRIC_SUCCESS -> {
                        biometricPrompt.authenticate(promptInfo)
                    }

                    BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                        showError = context.getString(R.string.biometric_unlock_not_enrolled)
                    }

                    else -> {
                        showError = context.getString(R.string.biometric_unlock_not_available)
                    }
                }
            } else {
                onChange(false)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(theme.surface)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = theme.onSurface,
                    modifier = Modifier.size(24.dp)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.settings_biometric_lock),
                        fontSize = 16.nonScaledSp,
                        fontWeight = FontWeight.Medium,
                        color = theme.onSurface
                    )

                    Text(
                        text = stringResource(R.string.settings_biometric_lock_description),
                        fontSize = 13.nonScaledSp,
                        color = theme.onSurface.copy(alpha = 0.7f),
                        lineHeight = 18.nonScaledSp
                    )
                }
            }

            Switch(
                checked = it,
                onCheckedChange = handleToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = theme.onPrimary,
                    checkedTrackColor = theme.primary,
                    uncheckedThumbColor = theme.onSurface.copy(alpha = 0.6f),
                    uncheckedTrackColor = theme.onSurface.copy(alpha = 0.3f)
                )
            )
        }
    }
}