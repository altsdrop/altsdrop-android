package com.altsdrop.feature.settings.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altsdrop.core.util.ToastManager
import com.altsdrop.feature.settings.domain.model.Setting
import com.altsdrop.feature.settings.domain.usecase.GetSettingsUseCase
import com.altsdrop.feature.settings.domain.usecase.GetUserDetailsUseCase
import com.altsdrop.feature.settings.domain.usecase.PostBugReportUseCase
import com.altsdrop.feature.settings.domain.usecase.PostFeedbackUseCase
import com.altsdrop.feature.settings.domain.usecase.UpdateNotificationSettingUseCase
import com.altsdrop.feature.settings.util.SettingConstants
import com.google.firebase.auth.FirebaseAuth
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
    private val getSettingsUseCase: GetSettingsUseCase,
    private val postFeedbackUseCase: PostFeedbackUseCase,
    private val postBugReportUseCase: PostBugReportUseCase,
    private val updateNotificationSettingUseCase: UpdateNotificationSettingUseCase,
    private val firebaseAuth: FirebaseAuth,
    private val toastManager: ToastManager
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

            is SettingsHomeScreenUiEvent.OnInputDialogConfirm -> {
                when (settingsHomeScreenUiEvent.settingId) {
                    SettingConstants.FEEDBACK -> {
                        submitFeedback(settingsHomeScreenUiEvent.inputText)
                    }

                    SettingConstants.REPORT_A_BUG -> {
                        submitBugReport(settingsHomeScreenUiEvent.inputText)
                    }
                }
            }
            is SettingsHomeScreenUiEvent.OnAlertDialogConfirm -> {
                when (settingsHomeScreenUiEvent.settingId) {
                    SettingConstants.LOGOUT -> {
                        firebaseAuth.signOut()
                        _uiState.update {
                            it.copy(
                                isUserLoggedIn = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun submitBugReport(message: String) {
        viewModelScope.launch {
            postBugReportUseCase(message).fold(
                onSuccess = {
                    toastManager.showToast("Bug report submitted successfully")
                },
                onFailure = {
                    toastManager.showToast("Failed to submit bug report")
                }
            )
        }
    }

    private fun submitFeedback(feedbackText: String) {
        viewModelScope.launch {
            postFeedbackUseCase(feedbackText).fold(
                onSuccess = {
                    toastManager.showToast("Feedback sent successfully")
                },
                onFailure = {
                    toastManager.showToast("Failed to send feedback")
                }
            )
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
                                    updateNotificationSettingUseCase.invoke(!setting.toggleState)
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