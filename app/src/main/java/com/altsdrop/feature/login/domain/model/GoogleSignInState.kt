package com.altsdrop.feature.login.domain.model

sealed class SignInState {
    data object Idle : SignInState()
    data object Loading : SignInState()
    data object Success : SignInState()
    data class Error(val message: String) : SignInState()
}