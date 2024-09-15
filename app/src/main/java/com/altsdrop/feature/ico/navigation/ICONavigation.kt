package com.altsdrop.feature.ico.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.altsdrop.feature.airdrop.ui.home.AirdropHomeRoute
import com.altsdrop.feature.ico.ui.home.ICOHomeRoute
import com.altsdrop.feature.ico.ui.home.ICOHomeScreen
import kotlinx.serialization.Serializable

@Serializable
object ICOScreen

fun NavController.navigateToICOScreen() {
    navigate(ICOScreen)
}

fun NavGraphBuilder.icoScreen() {
    composable<ICOScreen> {
        ICOHomeRoute()
    }
}