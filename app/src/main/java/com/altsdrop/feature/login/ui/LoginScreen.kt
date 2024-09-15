package com.altsdrop.feature.login.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LoginScreen(
        uiState = uiState,
       // onLoginClick = viewModel::onLoginClick
    )
}

@Composable
fun LoginScreen(
    uiState: LoginScreenUiState,
    //onLoginClick: () -> Unit
) {
    Column {
        Text(text = "Login")
    }
}