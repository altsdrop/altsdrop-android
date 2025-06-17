package com.altsdrop.feature.login.ui.login

import com.altsdrop.feature.login.domain.model.SignInState

data class LoginScreenUiState(
    val signInState: SignInState = SignInState.Idle,
)
