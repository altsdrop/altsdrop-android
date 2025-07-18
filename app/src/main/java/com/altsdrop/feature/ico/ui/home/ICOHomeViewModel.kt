package com.altsdrop.feature.ico.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ICOHomeViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(ICOHomeScreenUiState())
    val uiState = _uiState.asStateFlow()

}