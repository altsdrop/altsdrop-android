package com.altsdrop.app.ui.activity

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.altsdrop.R

@Immutable
data class MainActivityUiState(
    val tabs: List<Tab> = listOf(
        Tab(
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            feature = Feature.Airdrop,
            isSelected = true
        ),
        Tab(
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email,
            feature = Feature.News,
        ),
        Tab(
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            feature = Feature.Settings,
        ),
    )
)

data class Tab(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val feature: Feature,
    val isSelected: Boolean = false
)

sealed class Feature {
    data object Airdrop : Feature()
    data object News : Feature()
    data object Settings : Feature()
}