package com.altsdrop.app.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altsdrop.app.navigation.HomeScreen
import com.altsdrop.core.domain.CheckUserLoginUseCase
import com.altsdrop.feature.login.navigation.LoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val checkUserLoginUseCase: CheckUserLoginUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        MainActivityUiState(null)
    )
    val uiState = _uiState.asStateFlow()

    init {
        checkLogin()
    }

    private fun checkLogin() {
        viewModelScope.launch {
            if (checkUserLoginUseCase()) {
                _uiState.update {
                    it.copy(startDestination = HomeScreen)
                }
            } else {
                _uiState.update {
                    it.copy(startDestination = LoginScreen)
                }
            }
        }
    }

}