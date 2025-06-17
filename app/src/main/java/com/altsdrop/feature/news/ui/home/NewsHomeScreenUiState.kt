package com.altsdrop.feature.news.ui.home

import androidx.compose.runtime.Immutable
import com.altsdrop.feature.news.domain.model.Article

@Immutable
data class NewsHomeScreenUiState(
    val articles: List<Article> = emptyList(),
)
