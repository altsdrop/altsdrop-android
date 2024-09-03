package com.altsdrop.feature.news.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.altsdrop.feature.news.ui.home.NewsHomeRoute
import kotlinx.serialization.Serializable

@Serializable
object News

fun NavController.navigateToNews() {
    navigate(News)
}

fun NavGraphBuilder.newsScreen() {
    composable<News> {
        NewsHomeRoute()
    }
}