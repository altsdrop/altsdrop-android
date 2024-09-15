package com.altsdrop.app.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class HomeScreenUiState(
    val tabs: List<Tab> = listOf(
        Tab(
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            homeScreenTab = HomeScreenTab.Airdrop,
            isSelected = true
        ),
        Tab(
            selectedIcon = Icons.Filled.LocationOn,
            unselectedIcon = Icons.Outlined.LocationOn,
            homeScreenTab = HomeScreenTab.ICO,
        ),
        Tab(
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email,
            homeScreenTab = HomeScreenTab.News,
        ),
        Tab(
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            homeScreenTab = HomeScreenTab.Settings,
        ),
    )
)

data class Tab(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val homeScreenTab: HomeScreenTab,
    val isSelected: Boolean = false
)

sealed class HomeScreenTab {
    data object Airdrop : HomeScreenTab()
    data object ICO : HomeScreenTab()
    data object News : HomeScreenTab()
    data object Settings : HomeScreenTab()
}
