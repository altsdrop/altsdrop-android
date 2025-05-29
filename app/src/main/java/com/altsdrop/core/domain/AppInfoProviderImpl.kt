package com.altsdrop.core.domain

import android.content.Context
import android.os.Build

class AppInfoProviderImpl(private val context: Context) : AppInfoProvider {
    override val versionName: String
        get() = context.packageManager.getPackageInfo(context.packageName, 0).versionName
            ?: "Unknown"

    override val versionCode: Long
        get() {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                @Suppress("DEPRECATION")
                packageInfo.versionCode.toLong()
            }
        }
}
