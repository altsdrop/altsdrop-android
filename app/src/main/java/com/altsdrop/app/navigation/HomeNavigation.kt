package com.altsdrop.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.altsdrop.app.ui.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

fun NavController.navigateToHomeScreen(
    navOptions: NavOptions? = null
) {
    navigate(HomeScreen, navOptions = navOptions)
}

fun NavGraphBuilder.homeScreen(
    navigateToLogin: () -> Unit
) {
    composable<HomeScreen> {
        HomeRoute(
            navigateToLogin = navigateToLogin
        )
    }
}