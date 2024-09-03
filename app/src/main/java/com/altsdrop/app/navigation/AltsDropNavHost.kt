package com.altsdrop.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.altsdrop.app.ui.activity.Feature
import com.altsdrop.feature.airdrop.navigation.Airdrop
import com.altsdrop.feature.airdrop.navigation.airdropScreen
import com.altsdrop.feature.airdrop.navigation.navigateToAirdrop
import com.altsdrop.feature.news.navigation.News
import com.altsdrop.feature.news.navigation.navigateToNews
import com.altsdrop.feature.news.navigation.newsScreen
import com.altsdrop.feature.settings.navigation.Settings
import com.altsdrop.feature.settings.navigation.navigateToSettings
import com.altsdrop.feature.settings.navigation.settingsScreen

@Composable
fun AltsDropNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    feature: Feature,
) {
    val startDestination: Any = when (feature) {
        Feature.Airdrop -> Airdrop
        Feature.News -> News
        Feature.Settings -> Settings
    }

    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        airdropScreen()
        settingsScreen()
        newsScreen()
    }
}