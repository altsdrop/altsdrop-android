package com.altsdrop.app.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import com.altsdrop.R

data class HomeScreenUiState(
    val tabs: List<Tab> = listOf(
        Tab(
            selectedIcon = R.drawable.ic_airdrop_filled,
            unselectedIcon = R.drawable.ic_airdrop_outlined,
            homeScreenTab = HomeScreenTab.Airdrop,
            isSelected = true
        ),
//        Tab(
//            selectedIcon = Icons.Filled.LocationOn,
//            unselectedIcon = Icons.Outlined.LocationOn,
//            homeScreenTab = HomeScreenTab.ICO,
//        ),
        Tab(
            selectedIcon = R.drawable.ic_news_filled,
            unselectedIcon = R.drawable.ic_news_outlined,
            homeScreenTab = HomeScreenTab.News,
        ),
        Tab(
            selectedIcon = R.drawable.ic_settings_filled,
            unselectedIcon = R.drawable.ic_settings_outlined,
            homeScreenTab = HomeScreenTab.Settings,
        ),
    )
)

data class Tab(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val homeScreenTab: HomeScreenTab,
    val isSelected: Boolean = false
)

sealed class HomeScreenTab {
    data object Airdrop : HomeScreenTab()
  //  data object ICO : HomeScreenTab()
    data object News : HomeScreenTab()
    data object Settings : HomeScreenTab()
}
