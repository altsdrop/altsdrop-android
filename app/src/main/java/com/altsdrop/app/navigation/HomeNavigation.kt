package com.altsdrop.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.altsdrop.app.ui.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

fun NavController.navigateToHomeScreen() {
    navigate(HomeScreen)
}

fun NavGraphBuilder.homeScreen() {
    composable<HomeScreen> {
        HomeRoute()
    }
}