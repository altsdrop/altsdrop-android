package com.altsdrop.feature.dapp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.altsdrop.R
import com.altsdrop.core.ui.component.TextChip
import com.altsdrop.feature.dapp.domain.model.Dapp

@Composable
fun DappItem(
    modifier: Modifier,
    dappItem: Dapp
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(4.dp)
                    .aspectRatio(1f / 1f)
                    .clip(RoundedCornerShape(50.dp)), // Change dp for more/less rounding
                model = ImageRequest.Builder(LocalContext.current)
                    .data(dappItem.iconUrl)
                    .diskCachePolicy(CachePolicy.ENABLED)    // Enable disk cache
                    .memoryCachePolicy(CachePolicy.ENABLED)  // Enable memory cache
                    .apply {
                        if (dappItem.isIconUrlSvg) {
                            decoderFactory(SvgDecoder.Factory())
                        }
                    }
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(R.string.content_description_dapp),
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier,
                    text = dappItem.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(4.dp))

                FlowRow(
                    maxLines = 1,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    dappItem.tags.forEach { tag ->
                        TextChip(
                            text = tag,
                            contentPadding = PaddingValues(
                                horizontal = 12.dp,
                                vertical = 2.dp
                            ),
                            textStyle = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 8.sp
                            ),
                            borderColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            backgroundColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            textColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier,
            text = dappItem.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.5f
            ),
            maxLines = 2
        )
    }
}