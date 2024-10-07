package com.altsdrop.feature.news.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.altsdrop.feature.airdrop.navigation.AirdropDetailsScreen
import com.altsdrop.feature.airdrop.navigation.AirdropNavigationRoute
import com.altsdrop.feature.airdrop.navigation.AirdropScreen
import com.altsdrop.feature.airdrop.ui.airdrop_details.AirdropDetailsRoute
import com.altsdrop.feature.news.ui.details.ArticleDetailsRoute
import com.altsdrop.feature.news.ui.home.NewsHomeRoute
import kotlinx.serialization.Serializable

@Serializable
object NewsNavigationRoute

@Serializable
object NewsScreen

@Serializable
data class ArticleDetailsScreen(
    val slug: String
)

fun NavController.navigateToNews() {
    navigate(NewsScreen)
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.newsScreen(
    navigateToArticleDetails: (String) -> Unit,
    navigateBack: () -> Unit
) {
    navigation<NewsNavigationRoute>(startDestination = NewsScreen) {
        composable<NewsScreen> {
            NewsHomeRoute(
                navigateToArticleDetails = navigateToArticleDetails
            )
        }

        composable<ArticleDetailsScreen> { backStackEntry ->
            val articleDetailsScreen: ArticleDetailsScreen = backStackEntry.toRoute()
            ArticleDetailsRoute(
                slug = articleDetailsScreen.slug,
                navigateBack = navigateBack
            )
        }
    }
}