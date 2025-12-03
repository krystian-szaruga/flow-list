package com.olr261dn.flowlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.olr261dn.flowlist.biometric.BiometricAuthManager
import com.olr261dn.flowlist.navigation.NavigationLauncher
import com.olr261dn.flowlist.permissions.NotificationPermissionManager
import com.olr261dn.flowlist.validator.IntentValidator
import com.olr261dn.ui.theme.FlowListTheme
import com.olr261dn.viewmodel.PreferencesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private val preferencesViewModel: PreferencesViewModel by viewModels()
    private lateinit var intentValidator: IntentValidator
    private lateinit var biometricAuthManager: BiometricAuthManager
    private lateinit var notificationPermissionManager: NotificationPermissionManager

    companion object {
        private var isAuthenticatedThisSession = false
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeManagers()
        if (!intentValidator.isIntentValid(intent)) {
            finish()
            return
        }
        enableEdgeToEdge()
        notificationPermissionManager.checkAndRequest()
        when {
            !isAuthenticatedThisSession -> authenticateAndLaunch()
            else -> launchApp()
        }
    }

    private fun initializeManagers() {
        intentValidator = IntentValidator(this)
        biometricAuthManager = BiometricAuthManager(this, preferencesViewModel).apply {
            onAuthSuccess = {
                isAuthenticatedThisSession = true
                launchApp()
            }
            onAuthFailure = {
                finishAndRemoveTask()
            }
        }
        notificationPermissionManager = NotificationPermissionManager(this).apply {
            setup()
        }
    }

    private fun authenticateAndLaunch() {
        lifecycleScope.launch { biometricAuthManager.checkAndAuthenticate() }
    }

    private fun launchApp() {
        setContent {
            FlowListTheme {
                NavigationLauncher.Launcher()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        if (!intentValidator.isIntentValid(intent)) {
            Log.w(TAG, "onNewIntent: Invalid intent received, ignoring")
            return
        }
        super.onNewIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            isAuthenticatedThisSession = false
        }
    }
}