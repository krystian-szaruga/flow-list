package com.olr261dn.flowlist.validator

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager

internal class IntentValidator(private val activity: Activity) {
    private companion object {
        const val DEBUG: Boolean = false
    }

    fun isIntentValid(intent: Intent?): Boolean {
        val action = intent?.action ?: return false
        return when (action) {
            Intent.ACTION_MAIN -> isCalledBySystemOrLauncher()
            "com.olr261dn.flowlist.ACTION_OPEN_MAIN" -> isCalledByOwnApp()
            else -> false
        }
    }

    private fun isCalledBySystemOrLauncher(): Boolean {
        val callingPackage = activity.callingActivity?.packageName
        val hasLauncherCategory = activity.intent.categories
            ?.contains(Intent.CATEGORY_LAUNCHER) == true
        val isMainAction = activity.intent.action == Intent.ACTION_MAIN

        return when {
            isMainAction && callingPackage == null -> {
                DEBUG || hasLauncherCategory
            }

            callingPackage != null && isSystemLauncher(callingPackage) -> true
            else -> false
        }
    }

    private fun isCalledByOwnApp(): Boolean {
        val callingPackage = activity.callingActivity?.packageName
            ?: activity.referrer?.host
        return callingPackage == activity.packageName
    }

    private fun isSystemLauncher(packageName: String): Boolean {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
        }
        val resolveInfo = activity.packageManager.resolveActivity(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        )
        return resolveInfo?.activityInfo?.packageName == packageName
    }
}