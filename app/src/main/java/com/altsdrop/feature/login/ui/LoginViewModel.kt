package com.altsdrop.feature.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altsdrop.feature.login.domain.model.SignInState
import com.altsdrop.feature.login.domain.usecase.GoogleSignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val googleSignInUseCase: GoogleSignInUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun onSignInWithGoogleClick() {
        viewModelScope.launch {
            googleSignInUseCase().collectLatest { signInState ->
                when (signInState) {
                    SignInState.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoginSuccessful = true
                            )
                        }
                    }
                    else -> {
                        _uiState.update {
                            it.copy(isLoginSuccessful = false)
                        }
                    }
                }
            }
        }
    }
}