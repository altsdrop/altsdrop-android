package com.altsdrop.feature.dapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altsdrop.feature.dapp.domain.usecase.GetDappsByNetworkUseCase
import com.altsdrop.feature.dapp.ui.DappScreenUiAction.OnDappItemClick
import com.altsdrop.feature.dapp.ui.DappScreenUiAction.OnSearchBarClick
import com.altsdrop.feature.dapp.ui.DappScreenUiAction.OnSearchIconClick
import com.altsdrop.feature.dapp.ui.DappScreenUiAction.OnTabSelected
import com.altsdrop.feature.dapp.ui.DappScreenUiEvent.NavigateToDappWebsite
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
    private val getDappsByNetworkUseCase: GetDappsByNetworkUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DappScreenUiState())
    val uiState = _uiState.asStateFlow()
    private val _uiEvent = MutableSharedFlow<DappScreenUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getDappsByTab()
    }

    fun onAction(action: DappScreenUiAction) {
        when (action) {
            is OnTabSelected -> handleOnTabSelected(action.index)
            OnSearchBarClick, OnSearchIconClick -> navigateToSearchDappScreen()
            is OnDappItemClick -> handleOnDappItemClick(action.url)
        }
    }

    private fun handleOnDappItemClick(url: String) {
        viewModelScope.launch {
            _uiEvent.emit(NavigateToDappWebsite(url))
        }
    }

    private fun handleOnTabSelected(selectedTabIndex: Int) {
        _uiState.update {
            it.copy(
                selectedTabIndex = selectedTabIndex,
                selectedTabName = it.tabs[selectedTabIndex]
            )
        }

        getDappsByTab()
    }

    private fun getDappsByTab() {
        viewModelScope.launch {
            val currentState = _uiState.value
            val network = currentState.tabs[currentState.selectedTabIndex]
            val dapps = getDappsByNetworkUseCase(network)

            _uiState.update {
                it.copy(dAppsByTab = it.dAppsByTab + (network to dapps))
            }
        }
    }

    private fun navigateToSearchDappScreen() {
        viewModelScope.launch {
            _uiEvent.emit(NavigateToSearchDappScreen)
        }
    }
}