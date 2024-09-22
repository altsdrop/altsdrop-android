package com.altsdrop.feature.airdrop.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.altsdrop.app.ui.theme.AltsdropTheme
import com.altsdrop.core.ui.component.TextChip
import com.altsdrop.feature.airdrop.domain.model.Airdrop
import com.altsdrop.feature.airdrop.domain.model.previewAirdrop

@Composable
fun FeaturedAirdropCard(airdrop: Airdrop) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val cardWidth by remember {
        derivedStateOf {
            when {
                screenWidth < 600 -> screenWidth * 0.6f // Small screen, 60% width
                else -> screenWidth * 0.3f // Large screen, 30% width
            }
        }
    }

    val cardHeight by remember(cardWidth) {
        derivedStateOf {
            cardWidth * 9 / 16
        }
    }

    Card(
        modifier = Modifier
            .width(cardWidth.dp)
            .height(cardHeight.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        )
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = airdrop.thumbnail,
            contentScale = ContentScale.FillBounds,
            contentDescription = "featureBanner"
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AirdropCard(airdrop: Airdrop) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .wrapContentHeight(),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 1f),
                model = airdrop.thumbnail,
                contentScale = ContentScale.Crop,
                contentDescription = "featureBanner"
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface
                    )
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                FlowRow(
                    maxLines = 1,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    airdrop.tags.forEach { tag ->
                        TextChip(text = tag)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = airdrop.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )

                Text(
                    text = airdrop.description,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    maxLines = 2
                )
            }
        }
    }
}

@Preview
@Composable
fun AirdropCardPreview() {
    AltsdropTheme {
        AirdropCard(airdrop = previewAirdrop)
    }
}