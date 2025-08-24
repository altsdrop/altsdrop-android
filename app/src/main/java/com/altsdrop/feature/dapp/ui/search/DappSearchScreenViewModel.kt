package com.altsdrop.feature.dapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altsdrop.feature.dapp.domain.usecase.SearchDappsByNameOrUrlUseCase
import com.altsdrop.feature.dapp.ui.search.DappSearchScreenUiAction.OnBackClick
import com.altsdrop.feature.dapp.ui.search.DappSearchScreenUiAction.OnSearchQueryChanged
import com.altsdrop.feature.dapp.ui.search.DappSearchScreenUiEvent.NavigateBack
import com.altsdrop.feature.dapp.ui.search.DappSearchScreenUiEvent.NavigateToDappWebsite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DappSearchScreenViewModel @Inject constructor(
    private val searchDappsByNameOrUrlUseCase: SearchDappsByNameOrUrlUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DappSearchScreenUiState())
    val uiState = _uiState.asStateFlow()
    private val _uiEvent = MutableSharedFlow<DappSearchScreenUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()
    private var searchJob: Job? = null

    init {
        searchDapps("")
    }

    fun onAction(action: DappSearchScreenUiAction) {
        when (action) {
            is OnSearchQueryChanged -> handleSearchQueryChanged(action.query)
            OnBackClick -> viewModelScope.launch {
                _uiEvent.emit(NavigateBack)
            }

            is DappSearchScreenUiAction.OnDappItemClick -> handleOnDappItemClick(action.url)
        }
    }

    private fun handleOnDappItemClick(url: String) {
        viewModelScope.launch {
            _uiEvent.emit(NavigateToDappWebsite(url))
        }
    }

    private fun handleSearchQueryChanged(query: String) {
        _uiState.update { it.copy(query = query) }
        searchDapps(query)
    }

    private fun searchDapps(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)

            val dapps = searchDappsByNameOrUrlUseCase(query)

            _uiState.update {
                it.copy(
                    dapps = dapps,
                    shouldShowNoResults = dapps.isEmpty()
                )
            }
        }
    }
}