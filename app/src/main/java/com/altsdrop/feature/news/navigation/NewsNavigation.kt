package com.altsdrop.feature.news.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.altsdrop.feature.news.ui.home.NewsHomeRoute
import kotlinx.serialization.Serializable

@Serializable
object NewsScreen

fun NavController.navigateToNews() {
    navigate(NewsScreen)
}

fun NavGraphBuilder.newsScreen() {
    composable<NewsScreen> {
        NewsHomeRoute()
    }
}