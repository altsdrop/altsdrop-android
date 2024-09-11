package com.altsdrop.feature.settings.ui.home

import androidx.compose.runtime.Immutable
import com.altsdrop.feature.settings.domain.model.SettingsCategory
import com.altsdrop.feature.settings.domain.model.User

@Immutable
data class SettingsHomeScreenUiState(
    val user: User = User(),
    val categories: List<SettingsCategory> = emptyList()
)
