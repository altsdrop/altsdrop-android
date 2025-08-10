package com.altsdrop.feature.dapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.altsdrop.feature.dapp.ui.DappRoute
import com.altsdrop.feature.dapp.ui.search.DappSearchRoute
import kotlinx.serialization.Serializable

@Serializable
object DappNavigationGraphRoute

@Serializable
object DappRoute

@Serializable
object DappSearchRoute

fun NavController.navigateToDappSearchScreen() {
    navigate(DappSearchRoute) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.dAppsNavGraph(
    navigateToDappSearchScreen: () -> Unit,
    navigateBack: () -> Unit
) {
    navigation<DappNavigationGraphRoute>(startDestination = DappRoute) {
        composable<DappRoute> {
            DappRoute(
                navigateToDappSearchScreen = navigateToDappSearchScreen
            )
        }

        composable<DappSearchRoute> {
            DappSearchRoute(
                navigateBack = navigateBack
            )
        }
    }
}