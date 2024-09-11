package com.altsdrop.feature.settings.ui.home

sealed interface SettingsHomeScreenUiEvent {
    data class OnSettingClicked(val settingId: String) : SettingsHomeScreenUiEvent
}
