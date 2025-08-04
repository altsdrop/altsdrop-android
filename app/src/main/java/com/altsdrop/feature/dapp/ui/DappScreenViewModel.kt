package com.altsdrop.feature.dapp.ui

import androidx.lifecycle.ViewModel
import com.altsdrop.feature.dapp.ui.DappScreenUiAction.OnTabSelected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DappScreenViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(DappScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {}

    fun onAction(action: DappScreenUiAction) {
        when (action) {
            is OnTabSelected -> {
                _uiState.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }
}