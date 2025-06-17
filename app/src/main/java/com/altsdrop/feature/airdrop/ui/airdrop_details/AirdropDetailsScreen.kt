package com.altsdrop.feature.airdrop.ui.airdrop_details

import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.View
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.altsdrop.R
import com.altsdrop.app.ui.theme.AltsdropTheme
import com.altsdrop.app.ui.theme.hyperLink
import com.altsdrop.core.ui.component.ErrorInfo
import com.altsdrop.core.ui.component.TextChip
import com.altsdrop.core.util.openCustomTab
import com.altsdrop.feature.airdrop.domain.model.Airdrop
import com.altsdrop.feature.airdrop.domain.model.previewAirdrop
import com.altsdrop.feature.airdrop.utils.resolveDrawableByTitle
import com.altsdrop.feature.airdrop.utils.resolveDrawableByUrl

@ExperimentalMaterial3Api
@Composable
fun AirdropDetailsRoute(
    viewModel: AirdropDetailsViewModel = hiltViewModel(),
    slug: String,
    navigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAirdropDetails(slug = slug)
    }

    AirdropDetailsScreen(
        uiState = uiState,
        navigateBack = navigateBack
    )
}

@ExperimentalMaterial3Api
@Composable
fun AirdropDetailsScreen(
    uiState: AirdropDetailsScreenUiState,
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
                    text = stringResource(R.string.text_airdrop_overview),
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
            is AirdropDetailsScreenUiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is AirdropDetailsScreenUiState.Success -> {
                AirdropDetails(uiState.airdrop)
            }

            is AirdropDetailsScreenUiState.Error -> {
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
fun AirdropDetails(airdrop: Airdrop) {
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
                model = airdrop.thumbnail,
                contentScale = ContentScale.Crop,
                contentDescription = "featureBanner"
            )
        }

        Text(
            text = airdrop.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        FlowRow(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            airdrop.tags.forEach { tag ->
                TextChip(text = tag)
            }
        }

        Text(
            text = airdrop.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = stringResource(R.string.text_official_links),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        FlowRow(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            airdrop.officialLinks.forEach { tag ->
                TextChip(
                    text = tag.title,
                    onClick = {
                        context.openCustomTab(tag.url)
                    },
                    borderColor = MaterialTheme.colorScheme.onBackground,
                    backgroundColor = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.08f
                    ),
                    textColor = MaterialTheme.colorScheme.onBackground,
                    icon = ImageVector.vectorResource(resolveDrawableByTitle(tag.title))
                )
            }
        }

        Text(
            text = stringResource(R.string.text_social_links),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        FlowRow(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            airdrop.socialLinks.forEach { tag ->
                TextChip(
                    text = tag.title,
                    onClick = {
                        context.openCustomTab(tag.url)
                    },
                    borderColor = MaterialTheme.colorScheme.onBackground,
                    backgroundColor = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.08f
                    ),
                    textColor = MaterialTheme.colorScheme.onBackground,
                    icon = ImageVector.vectorResource(resolveDrawableByUrl(tag.url))
                )
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = stringResource(R.string.text_how_to_participate),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        airdrop.steps.forEachIndexed { index, step ->
            HtmlStepItem(index = index, html = step)
        }
    }
}

@Composable
fun HtmlStepItem(index: Int, html: String) {
    val context = LocalContext.current

    Row(modifier = Modifier) {
        Text(
            text = "${index + 1}. ",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f)
            )
        )

        val textColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f).toArgb()

        AndroidView(
            factory = {
                TextView(it).apply {
                    movementMethod = LinkMovementMethod.getInstance()
                    setTextColor(textColor)
                }
            },
            update = { textView ->
                val spanned = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
                val spannable = SpannableString(spanned)

                val urlSpans = spannable.getSpans(0, spannable.length, URLSpan::class.java)
                urlSpans.forEach { span ->
                    val start = spannable.getSpanStart(span)
                    val end = spannable.getSpanEnd(span)
                    val flags = spannable.getSpanFlags(span)
                    spannable.removeSpan(span)
                    spannable.setSpan(object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            context.openCustomTab(span.url)
                        }

                        override fun updateDrawState(ds: TextPaint) {
                            super.updateDrawState(ds)
                            ds.isUnderlineText = false
                            ds.color = hyperLink.toArgb()
                        }
                    }, start, end, flags)
                }

                textView.text = spannable
            },
            modifier = Modifier.weight(1f)
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
        AirdropDetailsScreen(
            uiState = AirdropDetailsScreenUiState.Loading
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
        AirdropDetailsScreen(
            uiState = AirdropDetailsScreenUiState.Success(
                airdrop = previewAirdrop
            )
        )
    }
}

