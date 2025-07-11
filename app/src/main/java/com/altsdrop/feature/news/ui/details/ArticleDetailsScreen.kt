package com.altsdrop.feature.news.ui.details

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.altsdrop.app.ui.theme.AltsdropTheme
import com.altsdrop.core.ui.component.ErrorInfo
import com.altsdrop.core.ui.component.FirebaseAsyncImage
import com.altsdrop.core.ui.component.TextChip
import com.altsdrop.core.ui.component.getHtmlText
import com.altsdrop.core.util.openCustomTab
import com.altsdrop.core.util.toRgba
import com.altsdrop.feature.news.domain.model.Article
import com.altsdrop.feature.news.domain.model.previewArticle

@ExperimentalMaterial3Api
@Composable
fun ArticleDetailsRoute(
    viewModel: ArticleDetailsViewModel = hiltViewModel(),
    slug: String,
    navigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getArticleDetails(slug = slug)
    }

    ArticleDetailsScreen(
        uiState = uiState,
        navigateBack = navigateBack
    )
}

@ExperimentalMaterial3Api
@Composable
fun ArticleDetailsScreen(
    uiState: ArticleDetailsScreenUiState,
    navigateBack: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        TopAppBar(
            title = {
                Text(
                    text = when (uiState) {
                        is ArticleDetailsScreenUiState.Success -> uiState.article.title
                        else -> ""
                    },
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = Ellipsis
                )
            },
            navigationIcon = {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            },
            windowInsets = WindowInsets(0, 0, 0, 0)
        )

        when (uiState) {
            is ArticleDetailsScreenUiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ArticleDetailsScreenUiState.Success -> {
                ArticleDetails(uiState.article)
            }

            is ArticleDetailsScreenUiState.Error -> {
                ErrorInfo(
                    message = uiState.message,
                    onRetry = { /*TODO*/ }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ArticleDetails(article: Article) {
    val scrollState = rememberScrollState()

    val backgroundColor = MaterialTheme.colorScheme.background.toRgba()
    val bodyColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f).toRgba()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
        ) {
            FirebaseAsyncImage(
                modifier = Modifier,
                imageUrl = article.headerImage,
                contentScale = ContentScale.Crop,
                contentDescription = "featureBanner"
            )
        }

        Text(
            text = article.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        FlowRow(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            article.tags.forEach { tag ->
                TextChip(text = tag)
            }
        }

        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            factory = { context ->
                WebView(context).apply {
                    // Load HTML content from the string
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView,
                            request: WebResourceRequest
                        ): Boolean {
                            context.openCustomTab(request.url.toString())
                            return true
                        }
                    }
                    loadDataWithBaseURL(
                        null,
                        getHtmlText(article.content, backgroundColor, bodyColor),
                        "text/html",
                        "UTF-8",
                        null
                    )
                    setPadding(0, 0, 0, 0)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    device = Devices.PIXEL_7
)
@Composable
fun AirdropHomeScreenLoadingPreview() {
    AltsdropTheme {
        ArticleDetailsScreen(
            uiState = ArticleDetailsScreenUiState.Loading
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    device = Devices.PIXEL_7
)
@Composable
fun AirdropHomeScreenSuccessPreview() {
    AltsdropTheme {
        ArticleDetailsScreen(
            uiState = ArticleDetailsScreenUiState.Success(
                article = previewArticle
            )
        )
    }
}

