package com.altsdrop.feature.news.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.altsdrop.core.navigation.DeepLinks
import com.altsdrop.feature.news.ui.details.NewsDetailsRoute
import com.altsdrop.feature.news.ui.home.NewsHomeRoute
import kotlinx.serialization.Serializable

@Serializable
object NewsNavigationRoute

@Serializable
object NewsScreen

@Serializable
data class NewsDetailsScreen(
    val slug: String
)

fun NavController.navigateToNews() {
    navigate(NewsScreen)
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.newsScreen(
    navigateToNewsDetails: (String) -> Unit,
    navigateBack: () -> Unit
) {
    navigation<NewsNavigationRoute>(startDestination = NewsScreen) {
        composable<NewsScreen>(
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = DeepLinks.NEWS
                }
            )
        ) {
            NewsHomeRoute(
                navigateToNewsDetails = navigateToNewsDetails
            )
        }

        composable<NewsDetailsScreen>(
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = DeepLinks.NEWS_DETAILS
                }
            )
        ) { backStackEntry ->
            val newsDetailsScreen: NewsDetailsScreen = backStackEntry.toRoute()
            NewsDetailsRoute(
                slug = newsDetailsScreen.slug,
                navigateBack = navigateBack
            )
        }
    }
}