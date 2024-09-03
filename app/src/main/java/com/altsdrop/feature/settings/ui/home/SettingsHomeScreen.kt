package com.altsdrop.feature.settings.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsHomeRoute(
    viewModel: SettingsHomeViewModel = hiltViewModel(),
) {
    SettingsHomeScreen()
}

@Composable
fun SettingsHomeScreen() {
    Column {
        Text(text = "Settings Home")
    }
}
