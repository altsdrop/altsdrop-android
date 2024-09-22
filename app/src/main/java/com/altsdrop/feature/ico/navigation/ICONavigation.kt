package com.altsdrop.feature.ico.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.altsdrop.feature.ico.ui.home.ICOHomeRoute
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