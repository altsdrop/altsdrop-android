package com.altsdrop.feature.news.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NewsHomeRoute(
    viewModel: NewsHomeViewModel = hiltViewModel(),
) {
    NewsHomeScreen()
}

@Composable
fun NewsHomeScreen() {
    Column {
        Text(text = "News Home")
    }
}