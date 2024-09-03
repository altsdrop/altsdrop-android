package com.altsdrop.feature.airdrop.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.altsdrop.feature.airdrop.ui.home.AirdropHomeRoute
import kotlinx.serialization.Serializable

@Serializable
object Airdrop

fun NavController.navigateToAirdrop() {
    navigate(Airdrop)
}

fun NavGraphBuilder.airdropScreen() {
    composable<Airdrop> {
        AirdropHomeRoute()
    }
}