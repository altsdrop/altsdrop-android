package com.altsdrop.feature.news.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.altsdrop.app.ui.theme.AltsdropTheme
import com.altsdrop.core.ui.component.ErrorInfo
import com.altsdrop.core.ui.component.TextChip
import com.altsdrop.feature.news.domain.model.Article
import com.altsdrop.feature.news.domain.model.previewArticle
import com.altsdrop.feature.news.util.ContentItem
import com.altsdrop.feature.news.util.parseHtmlContent

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
    val context = LocalContext.current
    val scrollState = rememberScrollState()

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
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(2f / 1f),
                model = article.headerImage,
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

        HtmlTextWithImagesAndLinks(article.content)
    }
}

@Composable
fun HtmlTextWithImagesAndLinks(
    htmlContent: String,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    val contentItems = remember { htmlContent.parseHtmlContent() }

    Column(modifier = modifier) {
        var index = 0 // Loop through contentItems

        while (index < contentItems.size) {
            when (val item = contentItems[index]) {
                is ContentItem.Heading -> {
                    Text(
                        text = item.text,
                        style = when (item.level) {
                            1 -> MaterialTheme.typography.headlineLarge
                            2 -> MaterialTheme.typography.headlineMedium
                            3 -> MaterialTheme.typography.headlineSmall
                            else -> MaterialTheme.typography.bodyLarge
                        },
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp) // Add padding for headings
                    )
                }

                is ContentItem.Image -> {
                    AsyncImage(
                        model = item.url,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth()  // Ensure full width
                            .aspectRatio(2f / 1f) // Maintain aspect ratio
                            .background(Color.Red),
                        contentScale = ContentScale.Crop,
                    )
                }

                is ContentItem.Text -> {
                    // Build the AnnotatedString for inline text and links
                    val annotatedString = buildAnnotatedString {
                        append(item.text) // Append the current text item

                        // Check for inline links and following text
                        while (index + 1 < contentItems.size) {
                            when (val nextItem = contentItems[index + 1]) {
                                is ContentItem.Link -> {
                                    // Append the link with a space before it
                                    append(" ") // Add space before the link
                                    val link =
                                        LinkAnnotation.Url(
                                            nextItem.url,
                                            TextLinkStyles(SpanStyle(color = Color.Blue))
                                        ) {
                                            val url = (it as LinkAnnotation.Url).url
                                            // log some metrics
                                            uriHandler.openUri(url)
                                        }
                                    withLink(link) { append(nextItem.text) }
                                    index++ // Skip the link item since it's been processed
                                }

                                is ContentItem.Text -> {
                                    // Append the following text with a space before it
                                    if (!nextItem.text.startsWith(".")) {
                                        append(" ")
                                    } // Add space before the next text
                                    append(nextItem.text) // Append the following text
                                    index++ // Skip the text item since it's been processed
                                }

                                else -> {
                                    break // Exit if it's not a text or link
                                }
                            }
                        }
                    }

                    // Render the Text with the annotated link
                    Text(
                        text = annotatedString,
                        modifier = Modifier,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                    )
                }

                is ContentItem.Link -> {
                    // Render links separately if not immediately after text
                    Text(
                        text = item.text,
                        modifier = Modifier
                            .clickable {
                                uriHandler.openUri(item.url)
                            },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Add spacing after headings or imag

            index++ // Move to the next item
        }
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

