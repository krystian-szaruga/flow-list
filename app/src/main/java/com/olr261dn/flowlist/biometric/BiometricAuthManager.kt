package com.olr261dn.flowlist.biometric

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.olr261dn.security.BiometricAuthActivity
import com.olr261dn.viewmodel.PreferencesViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

internal class BiometricAuthManager(
    private val activity: FragmentActivity,
    private val preferencesViewModel: PreferencesViewModel,
) {
    private val biometricLauncher = activity.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            onAuthSuccess()
        } else {
            onAuthFailure()
        }
    }
    var onAuthSuccess: () -> Unit = {}
    var onAuthFailure: () -> Unit = {}

    suspend fun checkAndAuthenticate() {
        val isBiometricEnabled = preferencesViewModel.biometricStatus
            .filterNotNull()
            .first()
        if (isBiometricEnabled) {
            biometricLauncher.launch(
                Intent(activity, BiometricAuthActivity::class.java)
            )
        } else {
            onAuthSuccess()
        }
    }
}