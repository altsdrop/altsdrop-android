package com.altsdrop.feature.dapp.ui.search

import androidx.compose.runtime.Immutable

@Immutable
data class DappSearchScreenUiState(
    val query: String = "",
)