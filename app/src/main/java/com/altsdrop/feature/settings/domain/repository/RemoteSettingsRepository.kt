package com.altsdrop.feature.settings.domain.repository

import com.altsdrop.feature.settings.domain.model.Setting

interface RemoteSettingsRepository {
    suspend fun getCommunitySettings(): List<Setting.Link>
    suspend fun getAboutSettings(): List<Setting.Link>
    suspend fun getSupportSettings(): List<Setting.Link>
}