package com.altsdrop.feature.airdrop.ui.airdrop_details

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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.altsdrop.R
import com.altsdrop.app.ui.theme.AltsdropTheme
import com.altsdrop.core.ui.component.ErrorInfo
import com.altsdrop.core.ui.component.FirebaseAsyncImage
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
            FirebaseAsyncImage(
                imageUrl = airdrop.thumbnail,
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
            text = airdrop.shortDescription,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
        )

        Text(
            text = stringResource(R.string.text_how_to_participate),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp
            ),
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        StepsDropdown(steps = airdrop.steps)

        Text(
            text = stringResource(R.string.text_official_links),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp
            ),
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
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp
            ),
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

        Text(
            text = stringResource(R.string.text_airdrop_description_title),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp
            ),
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = airdrop.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
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

