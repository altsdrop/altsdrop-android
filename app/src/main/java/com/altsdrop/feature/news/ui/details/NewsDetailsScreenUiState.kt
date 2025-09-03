package com.altsdrop.feature.news.ui.details

import com.altsdrop.feature.news.domain.model.News

sealed class NewsDetailsScreenUiState {
    data object Loading : NewsDetailsScreenUiState()
    data class Success(val news: News) : NewsDetailsScreenUiState()
    data class Error(val message: String) : NewsDetailsScreenUiState()
}
