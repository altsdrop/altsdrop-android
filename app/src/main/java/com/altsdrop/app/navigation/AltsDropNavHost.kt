package com.altsdrop.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.altsdrop.app.ui.home.HomeScreenTab
import com.altsdrop.feature.airdrop.navigation.AirdropDetailsScreen
import com.altsdrop.feature.airdrop.navigation.AirdropNavigationRoute
import com.altsdrop.feature.airdrop.navigation.airdropScreen
import com.altsdrop.feature.dapp.navigation.DappNavigationGraphRoute
import com.altsdrop.feature.dapp.navigation.dAppsNavGraph
import com.altsdrop.feature.login.navigation.LoginScreen
import com.altsdrop.feature.login.navigation.loginScreen
import com.altsdrop.feature.login.navigation.navigateToLoginScreen
import com.altsdrop.feature.news.navigation.ArticleDetailsScreen
import com.altsdrop.feature.news.navigation.NewsNavigationRoute
import com.altsdrop.feature.news.navigation.newsScreen
import com.altsdrop.feature.settings.navigation.SettingsScreen
import com.altsdrop.feature.settings.navigation.settingsScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: Any,
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(
            navigateToLogin = {
                navHostController.navigateToLoginScreen()
            }
        )
        loginScreen(
            navigateToHomeScreen = {
                navHostController.navigateToHomeScreen(
                    navOptions = navOptions {
                        popUpTo(LoginScreen) {
                            inclusive = true
                        }
                    }
                )
            }
        )
    }
}

@Composable
fun HomeScreenNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: HomeScreenTab,
    navigateToLogin: () -> Unit
) {
    val destination: Any = when (startDestination) {
        HomeScreenTab.Airdrop -> AirdropNavigationRoute
        //   HomeScreenTab.ICO -> ICOScreen
        HomeScreenTab.News -> NewsNavigationRoute
        HomeScreenTab.Settings -> SettingsScreen
        HomeScreenTab.DApps -> DappNavigationGraphRoute
    }

    NavHost(
        navController = navHostController,
        startDestination = destination,
        modifier = modifier,
    ) {
        airdropScreen(
            navigateToAirdropDetails = { slug ->
                navHostController.navigate(
                    route = AirdropDetailsScreen(
                        slug = slug
                    )
                )
            },
            navigateBack = navHostController::popBackStack
        )
        settingsScreen(
            navigateToLogin = navigateToLogin
        )
        newsScreen(
            navigateToArticleDetails = { slug ->
                navHostController.navigate(
                    route = ArticleDetailsScreen(
                        slug = slug
                    )
                )
            },
            navigateBack = navHostController::popBackStack
        )
        //icoScreen()
        dAppsNavGraph(
            navigateBack = navHostController::popBackStack
        )
    }
}