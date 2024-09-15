package com.altsdrop.feature.ico.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ICOHomeRoute(
    viewModel: ICOHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ICOHomeScreen(
        uiState = uiState
    )
}

@Composable
fun ICOHomeScreen(
    uiState: ICOHomeScreenUiState
) {

}