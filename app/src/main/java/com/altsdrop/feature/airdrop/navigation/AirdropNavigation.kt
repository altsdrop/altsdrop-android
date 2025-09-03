package com.altsdrop.feature.airdrop.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.altsdrop.core.navigation.DeepLinks
import com.altsdrop.feature.airdrop.ui.airdrop_details.AirdropDetailsRoute
import com.altsdrop.feature.airdrop.ui.home.AirdropHomeRoute
import kotlinx.serialization.Serializable

@Serializable
object AirdropNavigationRoute

@Serializable
object AirdropScreen

@Serializable
data class AirdropDetailsScreen(
    val slug: String,
)

fun NavController.navigateToAirdrop() {
    navigate(AirdropScreen)
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.airdropScreen(
    navigateBack: () -> Unit,
    navigateToAirdropDetails: (String) -> Unit
) {
    navigation<AirdropNavigationRoute>(
        startDestination = AirdropScreen
    ) {
        composable<AirdropScreen>(
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = DeepLinks.AIRDROPS
                }
            )
        ) {
            AirdropHomeRoute(
                navigateToAirdropDetails = navigateToAirdropDetails
            )
        }
        composable<AirdropDetailsScreen>(
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = DeepLinks.AIRDROP_DETAILS
                }
            )
        ) { backStackEntry ->
            val airdropDetailsScreen: AirdropDetailsScreen = backStackEntry.toRoute()
            AirdropDetailsRoute(
                slug = airdropDetailsScreen.slug,
                navigateBack = navigateBack
            )
        }
    }
}