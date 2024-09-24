package com.altsdrop.feature.airdrop.ui.airdrop_details

import com.altsdrop.feature.airdrop.domain.model.Airdrop

sealed class AirdropDetailsScreenUiState {
    data object Loading : AirdropDetailsScreenUiState()
    data class Success(val airdrop: Airdrop) : AirdropDetailsScreenUiState()
    data class Error(val message: String) : AirdropDetailsScreenUiState()
}
