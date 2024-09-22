package com.altsdrop.feature.settings.domain.usecase

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import com.altsdrop.R
import com.altsdrop.core.util.Resources
import com.altsdrop.feature.settings.domain.model.Setting
import com.altsdrop.feature.settings.domain.model.SettingsCategory
import com.altsdrop.feature.settings.util.SettingConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(private val resources: Resources) {

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

    private fun communitySettings() = SettingsCategory(
        categoryName = resources.getString(R.string.settings_category_community),  // settings_category_community
        settings = listOf(
            Setting.Link(
                id = SettingConstants.TELEGRAM,
                name = resources.getString(R.string.settings_telegram),  // settings_telegram
                description = "Join our Telegram community",
                url = "https://telegram.org"
            ),
            Setting.Link(
                id = SettingConstants.TWITTER,
                name = resources.getString(R.string.settings_twitter),  // settings_twitter
                description = "Follow us on Twitter (X)",
                url = "https://twitter.com"
            ),
            Setting.Link(
                id = SettingConstants.DISCORD,
                name = resources.getString(R.string.settings_discord),  // settings_discord
                description = "Join our Discord server",
                url = "https://discord.com"
            )
        )
    )

    private fun aboutSettings() = SettingsCategory(
        categoryName = resources.getString(R.string.settings_category_about),  // settings_category_about
        settings = listOf(
            Setting.Link(
                id = SettingConstants.PRIVACY_POLICY,
                name = resources.getString(R.string.settings_privacy_policy),  // settings_privacy_policy
                description = "Read our privacy policy",
                url = "https://discord.com"
            ),
            Setting.Link(
                id = SettingConstants.TERMS_OF_SERVICE,
                name = resources.getString(R.string.settings_terms_of_service),  // settings_terms_of_service
                description = "Read our terms of service",
                url = "https://discord.com"
            ),
            Setting.Link(
                id = SettingConstants.ABOUT_US,
                name = resources.getString(R.string.settings_about_us),  // settings_about_us
                description = "Learn more about us",
                url = "https://discord.com"
            )
        )
    )

    private fun supportSettings() = SettingsCategory(
        categoryName = resources.getString(R.string.settings_category_support),  // settings_category_support
        settings = listOf(
            Setting.Link(
                id = SettingConstants.CONTACT_US,
                name = resources.getString(R.string.settings_contact_us),  // settings_contact_us
                description = "Get in touch with us",
                url = "https://discord.com"
            ),
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
                title = resources.getString(R.string.report_issue_dialog_title),
                message = resources.getString(R.string.report_issue_dialog_message),
                confirmText = resources.getString(R.string.report_issue_dialog_confirm),
                cancelText = resources.getString(R.string.dialog_cancel),
                iconResId = R.drawable.baseline_report_24,
                id = SettingConstants.REPORT_AN_ISSUE,
                name = resources.getString(R.string.settings_report_an_issue),
                placeholderText = resources.getString(R.string.report_issue_input_field_placeholder),
                labelText = resources.getString(R.string.report_issue_input_field_label)
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