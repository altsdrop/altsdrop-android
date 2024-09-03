package com.altsdrop.app.ui.activity

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.altsdrop.R

@Immutable
data class MainActivityUiState(
    val tabs: List<Tab> = listOf(
        Tab(
            icon = Icons.Filled.Favorite,
            feature = Feature.Airdrop,
            isSelected = true
        ),
        Tab(
            icon = Icons.Filled.Email,
            feature = Feature.News,
        ),
        Tab(
            icon = Icons.Filled.Settings,
            feature = Feature.Settings,
        ),
    )
)

data class Tab(
    val icon: ImageVector,
    val feature: Feature,
    val isSelected: Boolean = false
)

sealed class Feature {
    data object Airdrop : Feature()
    data object News : Feature()
    data object Settings : Feature()
}