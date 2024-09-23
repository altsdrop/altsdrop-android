package com.altsdrop.feature.settings.domain.usecase

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import com.altsdrop.R
import com.altsdrop.core.util.Resources
import com.altsdrop.feature.settings.domain.model.Setting
import com.altsdrop.feature.settings.domain.model.SettingsCategory
import com.altsdrop.feature.settings.domain.repository.RemoteSettingsRepository
import com.altsdrop.feature.settings.util.SettingConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val resources: Resources,
    private val remoteSettingsRepository: RemoteSettingsRepository
) {
    suspend operator fun invoke(): List<SettingsCategory> = withContext(Dispatchers.IO) {
        listOf(
            generalSettings(),
            communitySettings(),
            aboutSettings(),
            supportSettings(),
            logoutSetting()
        )
    }

    private fun generalSettings() = SettingsCategory(
        categoryName = resources.getString(R.string.settings_category_general),  // settings_category_general
        settings = listOf(
            Setting.Toggle(
                id = SettingConstants.PUSH_NOTIFICATIONS,
                name = resources.getString(R.string.settings_push_notifications),  // settings_push_notifications
                description = "Enable or disable push notifications",
                toggleState = true
            )
        )
    )

    private suspend fun communitySettings() = SettingsCategory(
        categoryName = resources.getString(R.string.settings_category_community),  // settings_category_community
        settings = remoteSettingsRepository.getCommunitySettings()
    )

    private suspend fun aboutSettings() = SettingsCategory(
        categoryName = resources.getString(R.string.settings_category_about),  // settings_category_about
        settings = remoteSettingsRepository.getAboutSettings()
    )

    private suspend fun supportSettings() = SettingsCategory(
        categoryName = resources.getString(R.string.settings_category_support),  // settings_category_support
        settings = remoteSettingsRepository.getSupportSettings() + listOf(
            Setting.InputDialog(
                title = resources.getString(R.string.feedback_dialog_title),
                message = resources.getString(R.string.feedback_dialog_message),
                confirmText = resources.getString(R.string.feedback_dialog_confirm),
                cancelText = resources.getString(R.string.dialog_cancel),
                iconResId = R.drawable.baseline_feedback_24,
                id = SettingConstants.FEEDBACK,
                name = resources.getString(R.string.settings_feedback),
                placeholderText = resources.getString(R.string.feedback_input_field_placeholder),
                labelText = resources.getString(R.string.feedback_input_field_label)
            ),
            Setting.InputDialog(
                title = resources.getString(R.string.report_bug_dialog_title),
                message = resources.getString(R.string.report_bug_dialog_message),
                confirmText = resources.getString(R.string.report_bug_dialog_confirm),
                cancelText = resources.getString(R.string.dialog_cancel),
                iconResId = R.drawable.baseline_report_24,
                id = SettingConstants.REPORT_A_BUG,
                name = resources.getString(R.string.settings_report_a_bug),
                placeholderText = resources.getString(R.string.report_bug_dialog_input_field_placeholder),
                labelText = resources.getString(R.string.report_bug_dialog_input_field_label)
            )
        )
    )

    private fun logoutSetting() = SettingsCategory(
        categoryName = resources.getString(R.string.settings_category_account),  // settings_logout
        settings = listOf(
            Setting.AlertDialog(
                title = resources.getString(R.string.logout_dialog_title),
                message = resources.getString(R.string.logout_dialog_message),
                confirmText = resources.getString(R.string.logout_dialog_confirm),
                cancelText = resources.getString(R.string.dialog_cancel),
                icon = Icons.Default.Info,
                id = SettingConstants.LOGOUT,
                name = resources.getString(R.string.settings_logout),
                description = "Log out of your account",
            )
        )
    )
}