package com.altsdrop.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.altsdrop.feature.login.ui.login.LoginRoute
import kotlinx.serialization.Serializable

@Serializable
object LoginScreen

fun NavController.navigateToLoginScreen() {
    navigate(LoginScreen)
}

fun NavGraphBuilder.loginScreen(
    navigateToHomeScreen: () -> Unit
) {
    composable<LoginScreen> {
        LoginRoute(
            navigateToHomeScreen = {
                navigateToHomeScreen()
            }
        )
    }
}