package com.altsdrop.feature.dapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altsdrop.feature.dapp.ui.DappScreenUiAction.OnSearchBarClick
import com.altsdrop.feature.dapp.ui.DappScreenUiAction.OnSearchIconClick
import com.altsdrop.feature.dapp.ui.DappScreenUiAction.OnTabSelected
import com.altsdrop.feature.dapp.ui.DappScreenUiEvent.NavigateToSearchDappScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DappScreenViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(DappScreenUiState())
    val uiState = _uiState.asStateFlow()
    private val _uiEvent = MutableSharedFlow<DappScreenUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onAction(action: DappScreenUiAction) {
        when (action) {
            is OnTabSelected -> {
                _uiState.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }

            OnSearchBarClick, OnSearchIconClick -> navigateToSearchDappScreen()
        }
    }

    private fun navigateToSearchDappScreen() {
        viewModelScope.launch {
            _uiEvent.emit(NavigateToSearchDappScreen)
        }
    }
}