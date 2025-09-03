package com.altsdrop.feature.news.ui.home

import androidx.compose.runtime.Immutable
import com.altsdrop.feature.news.domain.model.News

@Immutable
data class NewsHomeScreenUiState(
    val news: List<News> = emptyList(),
)
