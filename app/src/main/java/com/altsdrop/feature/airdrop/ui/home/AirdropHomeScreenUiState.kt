package com.altsdrop.feature.airdrop.ui.home

import com.altsdrop.R
import com.altsdrop.feature.airdrop.domain.model.Airdrop

data class AirdropHomeScreenUiState(
    val featuredAirdrops: List<Airdrop> = emptyList(),
    val newAirdrops: List<Airdrop> = emptyList(),
    val highlyRatedAirdrops: List<Airdrop> = emptyList(),
    val exploreAirdrops: List<Airdrop> = emptyList(),
    val tabs: List<Int> = listOf(
        R.string.new_tab,
        R.string.highly_rated_tab,
        R.string.explore_tab
    ),
    val selectedTabIndex: Int = 0
)
