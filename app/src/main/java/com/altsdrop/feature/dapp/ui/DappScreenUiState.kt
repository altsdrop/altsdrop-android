package com.altsdrop.feature.dapp.ui

import androidx.compose.runtime.Immutable
import com.altsdrop.feature.dapp.domain.model.Dapp

@Immutable
data class DappScreenUiState(
    val tabs: List<String> = listOf(
        "All", "Ethereum", "Solana", "Base", "BNB Chain", "Sui",
        "Avalanche", "Tron", "Optimism"
    ),
    val dAppsByTab: Map<String, List<Dapp>> = emptyMap(),
    val selectedTabIndex: Int = 0,
    val selectedTabName: String = "All"
)
