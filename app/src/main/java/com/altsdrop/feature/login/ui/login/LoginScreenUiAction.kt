package com.altsdrop.feature.login.ui.login

import android.content.Context

sealed class LoginScreenUiAction {
    data class OnLoginClick(val context: Context?) : LoginScreenUiAction()
}