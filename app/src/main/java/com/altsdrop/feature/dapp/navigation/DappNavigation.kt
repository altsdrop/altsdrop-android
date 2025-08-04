package com.altsdrop.feature.dapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.altsdrop.feature.dapp.ui.DappRoute
import kotlinx.serialization.Serializable

@Serializable
object DappNavigationGraphRoute

@Serializable
object DappRoute

fun NavGraphBuilder.dAppsNavGraph(
    navigateBack: () -> Unit
) {
    navigation<DappNavigationGraphRoute>(startDestination = DappRoute) {
        composable<DappRoute> {
            DappRoute()
        }
    }
}