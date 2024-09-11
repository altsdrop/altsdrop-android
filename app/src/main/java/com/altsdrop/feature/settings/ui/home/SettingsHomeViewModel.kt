package com.altsdrop.feature.settings.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altsdrop.feature.settings.domain.model.Setting
import com.altsdrop.feature.settings.domain.usecase.GetSettingsUseCase
import com.altsdrop.feature.settings.domain.usecase.GetUserDetailsUseCase
import com.altsdrop.feature.settings.util.SettingConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsHomeViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsHomeScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getSettings()
        getUserDetails()
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    user = getUserDetailsUseCase()
                )
            }
        }
    }

    private fun getSettings() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    categories = getSettingsUseCase()
                )
            }
        }
    }

    fun onEvent(settingsHomeScreenUiEvent: SettingsHomeScreenUiEvent) {
        when (settingsHomeScreenUiEvent) {
            is SettingsHomeScreenUiEvent.OnSettingClicked -> {
                when (settingsHomeScreenUiEvent.settingId) {
                    SettingConstants.PUSH_NOTIFICATIONS -> {
                        togglePushNotifications()
                    }
                }
            }
        }
    }

    private fun togglePushNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { currentUiState ->
                currentUiState.copy(
                    categories = currentUiState.categories.map { category ->
                        category.copy(
                            settings = category.settings.map { setting ->
                                if (setting.id == SettingConstants.PUSH_NOTIFICATIONS &&
                                    setting is Setting.Toggle
                                ) {
                                    setting.copy(toggleState = !setting.toggleState)
                                } else {
                                    setting
                                }
                            }
                        )
                    }
                )
            }
        }
    }
}