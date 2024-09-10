package com.altsdrop.feature.news.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.transform.Transformation
import com.altsdrop.R
import com.altsdrop.feature.news.domain.model.Article

@Composable
fun NewsHomeRoute(
    viewModel: NewsHomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NewsHomeScreen(
        uiState = uiState
    )
}

@Composable
fun NewsHomeScreen(uiState: NewsHomeScreenUiState) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.title_featured_news),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }


            items(
                items = uiState.articles,
                key = { article ->
                    article.id
                }
            ) { article ->
                ArticleCard(article = article)
            }
        }
    }

}

@Composable
fun ArticleCard(article: Article) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(16.dp) // Start alignment, but text will expand
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
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

            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = article.publishedDate,
                color = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.6f
                ),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start
            )
        }

        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = article.headerImage,
            contentScale = ContentScale.FillBounds,
            contentDescription = "article image"
        )
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
        article = Article(
            title = "Blast L2",
            headerImage = "",
            category = "Finance"
        )
    )
}