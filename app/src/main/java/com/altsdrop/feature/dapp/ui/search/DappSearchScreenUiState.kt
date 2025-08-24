package com.altsdrop.feature.dapp.ui.search

import androidx.compose.runtime.Immutable
import com.altsdrop.feature.dapp.domain.model.Dapp

@Immutable
data class DappSearchScreenUiState(
    val query: String = "",
    val dapps: List<Dapp> = emptyList(),
    val shouldShowNoResults: Boolean = false
)