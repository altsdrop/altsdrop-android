package com.altsdrop.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun onTabClicked(selectedTab: Tab) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                tabs = _uiState.value.tabs.map { tab ->
                    tab.copy(isSelected = tab == selectedTab)
                }
            )
        }
    }
}