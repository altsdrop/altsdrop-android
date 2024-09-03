package com.altsdrop.feature.airdrop.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AirdropHomeRoute(
    viewModel: AirdropHomeViewModel = hiltViewModel(),
) {
    AirdropHomeScreen()
}

@Composable
fun AirdropHomeScreen() {
    Column {
        Text(text = "Airdrop Home")
    }
}
