package com.altsdrop.core.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey

object PreferencesKeys {
    val NOTIFICATION_ENABLED = booleanPreferencesKey("notification_enabled")
    val DAPPS_LAST_UPDATE = longPreferencesKey("dapps_last_update")
}