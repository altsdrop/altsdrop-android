package com.altsdrop.core.ui.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest

@Composable
fun FirebaseAsyncImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    alignment: Alignment = Alignment.Center
) {
    AsyncImage(
        modifier = modifier
            .aspectRatio(2f / 1f),
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .diskCachePolicy(CachePolicy.ENABLED)    // Enable disk cache
            .memoryCachePolicy(CachePolicy.ENABLED)  // Enable memory cache
            .build(),
        contentScale = contentScale,
        contentDescription = contentDescription,
        alignment = alignment
    )
}