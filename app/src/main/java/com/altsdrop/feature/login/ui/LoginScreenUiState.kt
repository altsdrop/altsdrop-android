package com.altsdrop.feature.login.ui

import com.altsdrop.feature.login.domain.model.SignInState

data class LoginScreenUiState(
    val signInState: SignInState = SignInState.Idle,
)
