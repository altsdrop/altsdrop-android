package com.altsdrop.feature.dapp.ui

import androidx.compose.runtime.Immutable
import com.altsdrop.feature.dapp.domain.Dapp

@Immutable
data class DappScreenUiState(
    val tabs: List<String> = listOf(
        "All", "Ethereum", "Solana", "Base", "BNB Chain", "Sui",
        "Avalanche", "Tron", "Optimism"
    ),
    val allDapp: List<Dapp> = emptyList(),
    val selectedTabIndex: Int = 0,
)
