package com.altsdrop.feature.settings.domain.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.altsdrop.core.util.NotificationTopic
import com.altsdrop.core.util.PreferencesKeys
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateNotificationSettingUseCase @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val firebase: Firebase
) {
    suspend operator fun invoke(enabled: Boolean): Unit = withContext(Dispatchers.IO) {
        setNotificationEnabled(enabled)
        if (enabled) {
            subscribeToTopic()
        } else {
            unsubscribeFromTopic()
        }
    }

    private fun unsubscribeFromTopic() {
        firebase.messaging.unsubscribeFromTopic(NotificationTopic.AIRDROP)
    }

    private fun subscribeToTopic() {
        firebase.messaging.subscribeToTopic(NotificationTopic.AIRDROP)
    }

    private suspend fun setNotificationEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.NOTIFICATION_ENABLED] = enabled
        }
    }
}