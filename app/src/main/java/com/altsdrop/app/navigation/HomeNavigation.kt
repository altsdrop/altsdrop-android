package com.altsdrop.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.altsdrop.app.ui.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

fun NavController.navigateToHomeScreen(
    navOptions: NavOptions? = null
) {
    navigate(HomeScreen, navOptions = navOptions)
}

fun NavGraphBuilder.homeScreen() {
    composable<HomeScreen> {
        HomeRoute()
    }
}