package com.altsdrop.feature.login.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altsdrop.feature.login.domain.model.SignInState
import com.altsdrop.feature.login.domain.usecase.GoogleSignInUseCase
import com.altsdrop.feature.login.ui.login.LoginScreenUiAction.OnLoginClick
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val googleSignInUseCase: GoogleSignInUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: LoginScreenUiAction) {
        when (action) {
            is OnLoginClick -> onSignInWithGoogleClick(action.context)
        }
    }

    fun onSignInWithGoogleClick(context: Context?) {
        if (context == null) {
            FirebaseCrashlytics.getInstance().log(
                "LoginViewModel.onSignInWithGoogleClick: context is null"
            )

            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    signInState = SignInState.Loading
                )
            }
            _uiState.update {
                it.copy(
                    signInState = googleSignInUseCase(context)
                )
            }
        }
    }
}