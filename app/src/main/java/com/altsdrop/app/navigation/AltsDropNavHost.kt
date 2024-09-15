package com.altsdrop.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.altsdrop.app.ui.home.HomeScreenTab
import com.altsdrop.feature.airdrop.navigation.AirdropScreen
import com.altsdrop.feature.airdrop.navigation.airdropScreen
import com.altsdrop.feature.ico.navigation.ICOScreen
import com.altsdrop.feature.ico.navigation.icoScreen
import com.altsdrop.feature.login.navigation.loginScreen
import com.altsdrop.feature.news.navigation.NewsScreen
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
        homeScreen()
        loginScreen()
    }
}

@Composable
fun HomeScreenNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: HomeScreenTab,
) {
    val destination: Any = when (startDestination) {
        HomeScreenTab.Airdrop -> AirdropScreen
        HomeScreenTab.ICO -> ICOScreen
        HomeScreenTab.News -> NewsScreen
        HomeScreenTab.Settings -> SettingsScreen
    }

    NavHost(
        navController = navHostController,
        startDestination = destination,
        modifier = modifier,
    ) {
        airdropScreen()
        settingsScreen()
        newsScreen()
        icoScreen()
    }
}