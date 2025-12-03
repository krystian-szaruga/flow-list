package com.olr261dn.security

import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.olr261dn.resources.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BiometricAuthActivity : FragmentActivity() {
    private var biometricPrompt: BiometricPrompt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            delay(100)
            checkBiometricAvailability()
        }
    }

    private fun checkBiometricAvailability() {
        val biometricManager = BiometricManager.from(this)

        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                showBiometricPrompt()
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Toast.makeText(
                    this,
                    getString(R.string.biometric_unlock_not_enrolled),
                    Toast.LENGTH_SHORT
                ).show()
                setResult(RESULT_OK)
                finish()
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Toast.makeText(
                    this,
                    getString(R.string.biometric_unlock_no_hardware),
                    Toast.LENGTH_LONG
                ).show()
                setResult(RESULT_OK)
                finish()
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Toast.makeText(
                    this,
                    getString(R.string.biometric_unlock_unavailable),
                    Toast.LENGTH_LONG
                ).show()
                setResult(RESULT_OK)
                finish()
            }
            else -> {
                Toast.makeText(
                    this,
                    getString(R.string.biometric_unlock_not_available),
                    Toast.LENGTH_LONG
                ).show()
                setResult(RESULT_CANCELED)
                finish()
            }
        }
    }

    private fun showBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(
            this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.biometric_unlock_successful),
                        Toast.LENGTH_SHORT
                    ).show()
                    setResult(RESULT_OK)
                    finish()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    when (errorCode) {
                        BiometricPrompt.ERROR_NEGATIVE_BUTTON,
                        BiometricPrompt.ERROR_USER_CANCELED -> {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.biometric_unlock_cancelled),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        BiometricPrompt.ERROR_LOCKOUT,
                        BiometricPrompt.ERROR_LOCKOUT_PERMANENT -> {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.biometric_unlock_lockout),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else -> {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.biometric_unlock_error, errString),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    setResult(RESULT_CANCELED)
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.biometric_unlock_failed_retry),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometric_unlock_title))
            .setSubtitle(getString(R.string.biometric_unlock_subtitle))
            .setDescription(getString(R.string.biometric_unlock_description))
            .setNegativeButtonText(getString(R.string.biometric_unlock_cancel))
            .setConfirmationRequired(false)
            .build()

        biometricPrompt?.authenticate(promptInfo)
    }

    override fun onDestroy() {
        super.onDestroy()
        biometricPrompt = null
    }

    override fun onPause() {
        super.onPause()
        biometricPrompt?.cancelAuthentication()
    }
}