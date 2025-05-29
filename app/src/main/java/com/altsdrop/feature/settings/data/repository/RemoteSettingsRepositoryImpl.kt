package com.altsdrop.feature.settings.data.repository

import com.altsdrop.feature.settings.data.model.RemoteSettingsDTO
import com.altsdrop.feature.settings.domain.model.Setting
import com.altsdrop.feature.settings.domain.repository.RemoteSettingsRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteSettingsRepositoryImpl(
    private val remoteConfig: FirebaseRemoteConfig,
    private val moshi: Moshi
) : RemoteSettingsRepository {

    override suspend fun getCommunitySettings() = withContext(Dispatchers.IO) {
        parseRemoteSettings().community.map { linkDTO ->
            Setting.Link(
                name = linkDTO.title,
                url = linkDTO.url
            )
        }
    }

    override suspend fun getAboutSettings() = withContext(Dispatchers.IO) {
        parseRemoteSettings().about.map { linkDTO ->
            Setting.Link(
                name = linkDTO.title,
                url = linkDTO.url
            )
        }
    }

    override suspend fun getSupportSettings() = withContext(Dispatchers.IO) {
        parseRemoteSettings().support.map { linkDTO ->
            Setting.Link(
                name = linkDTO.title,
                url = linkDTO.url
            )
        }
    }

    private fun parseRemoteSettings(): RemoteSettingsDTO {
        return try {
            val adapter = moshi.adapter(RemoteSettingsDTO::class.java)
            val json = remoteConfig.getString("settings")
            adapter.fromJson(json) ?: RemoteSettingsDTO()
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            RemoteSettingsDTO()
        }
    }
}