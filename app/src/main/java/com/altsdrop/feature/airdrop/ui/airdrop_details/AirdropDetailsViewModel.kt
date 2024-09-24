package com.altsdrop.feature.airdrop.ui.airdrop_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altsdrop.R
import com.altsdrop.core.util.Resources
import com.altsdrop.feature.airdrop.domain.usecase.GetAirdropDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirdropDetailsViewModel @Inject constructor(
    private val resources: Resources,
    private val getAirdropDetailsUseCase: GetAirdropDetailsUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<AirdropDetailsScreenUiState>(AirdropDetailsScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getAirdropDetails(slug: String) {
        viewModelScope.launch {
            getAirdropDetailsUseCase(slug).fold(
                onSuccess = { airdrop ->
                    _uiState.value = AirdropDetailsScreenUiState.Success(
                        airdrop = airdrop
                    )
                },
                onFailure = {
                    _uiState.value = AirdropDetailsScreenUiState.Error(
                        resources.getString(
                            R.string.error_airdrop_not_found
                        )
                    )
                }
            )
        }
    }
}