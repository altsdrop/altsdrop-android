package com.altsdrop.feature.settings.ui.home

sealed interface SettingsHomeScreenUiEvent {
    data class OnSettingClicked(val settingId: String) : SettingsHomeScreenUiEvent
    data class OnInputDialogConfirm(
        val settingId: String,
        val inputText: String
    ) : SettingsHomeScreenUiEvent
    data class OnAlertDialogConfirm(
        val settingId: String
    ) : SettingsHomeScreenUiEvent
}
