package com.altsdrop.feature.settings.data.repository

import com.altsdrop.feature.settings.data.model.RemoteSettingsDTO
import com.altsdrop.feature.settings.domain.model.Setting
import com.altsdrop.feature.settings.domain.repository.RemoteSettingsRepository
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteSettingsRepositoryImpl(
    private val remoteConfig: FirebaseRemoteConfig,
    private val gson: Gson
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
        val remoteSettings = remoteConfig.getString("settings")
        return gson.fromJson(remoteSettings, RemoteSettingsDTO::class.java)
    }
}