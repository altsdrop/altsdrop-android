package com.altsdrop.core.domain

interface AppInfoProvider {
    val versionName: String
    val versionCode: Long
}