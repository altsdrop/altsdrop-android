package com.altsdrop.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.altsdrop.feature.airdrop.ui.home.AirdropHomeRoute
import com.altsdrop.feature.login.ui.LoginRoute
import kotlinx.serialization.Serializable

@Serializable
object LoginScreen

fun NavController.navigateToLoginScreen() {
    navigate(LoginScreen)
}

fun NavGraphBuilder.loginScreen() {
    composable<LoginScreen> {
        LoginRoute()
    }
}