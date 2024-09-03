package com.altsdrop.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.altsdrop.feature.settings.ui.home.SettingsHomeRoute
import kotlinx.serialization.Serializable

@Serializable
object Settings

fun NavController.navigateToSettings() {
    navigate(Settings)
}

fun NavGraphBuilder.settingsScreen() {
    composable<Settings> {
        SettingsHomeRoute()
    }
}