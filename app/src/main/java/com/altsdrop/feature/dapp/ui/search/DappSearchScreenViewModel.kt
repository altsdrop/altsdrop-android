package com.altsdrop.feature.dapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altsdrop.feature.dapp.ui.search.DappSearchScreenUiEvent.NavigateBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DappSearchScreenViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(DappSearchScreenUiState())
    val uiState = _uiState.asStateFlow()
    private val _uiEvent = MutableSharedFlow<DappSearchScreenUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onAction(action: DappSearchScreenUiAction) {
        when (action) {
            is DappSearchScreenUiAction.OnSearchQueryChanged -> {
                _uiState.update { it.copy(query = action.query) }
            }

            DappSearchScreenUiAction.OnBackClick -> viewModelScope.launch {
                _uiEvent.emit(NavigateBack)
            }
        }
    }
}