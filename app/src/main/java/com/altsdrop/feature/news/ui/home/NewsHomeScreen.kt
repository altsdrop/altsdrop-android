package com.altsdrop.feature.news.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.altsdrop.R
import com.altsdrop.core.ui.component.FirebaseAsyncImage
import com.altsdrop.core.ui.component.TextChip
import com.altsdrop.feature.news.domain.model.Article
import com.altsdrop.feature.news.domain.model.previewArticle

@Composable
fun NewsHomeRoute(
    viewModel: NewsHomeViewModel = hiltViewModel(),
    navigateToArticleDetails: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NewsHomeScreen(
        uiState = uiState,
        onArticleClicked = navigateToArticleDetails
    )
}

@Composable
fun NewsHomeScreen(
    uiState: NewsHomeScreenUiState,
    onArticleClicked: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.title_feature_news),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }


            items(
                items = uiState.articles,
                key = { article ->
                    article.slug
                }
            ) { article ->
                ArticleCard(
                    article = article,
                    onArticleClicked = {
                        onArticleClicked(article.slug)
                    }
                )
            }
        }
    }

}

@Composable
fun ArticleCard(
    article: Article,
    onArticleClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onArticleClicked()
            },
        horizontalArrangement = Arrangement.spacedBy(16.dp) // Start alignment, but text will expand
    ) {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(8.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(article.headerImage)
                .diskCachePolicy(CachePolicy.ENABLED)    // Enable disk cache
                .memoryCachePolicy(CachePolicy.ENABLED)  // Enable memory cache
                .build(),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            contentDescription = "article image"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = article.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .wrapContentWidth()
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start
                ) {
                    TextChip(
                        text = if (article.readTime == 1) "${article.readTime} min read" else
                            "${article.readTime} mins read",
                        borderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        backgroundColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                            alpha = 0f
                        ),
                        textColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f),
                    text = article.publishedDateTime,
                    color = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.6f
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.End,
                )

                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

@Preview
@Composable
fun NewsHomeScreenPreview() {
    NewsHomeScreen(
        uiState = NewsHomeScreenUiState(
            articles = listOf(
                Article(
                    "1",
                    "Blast L2",
                )
            )
        )
    )
}

@Preview
@Composable
fun ArticleCardPreview() {
    ArticleCard(
        article = previewArticle
    )
}