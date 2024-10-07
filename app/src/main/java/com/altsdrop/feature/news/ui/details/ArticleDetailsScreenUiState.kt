package com.altsdrop.feature.news.ui.details

import com.altsdrop.feature.news.domain.model.Article

sealed class ArticleDetailsScreenUiState {
    data object Loading : ArticleDetailsScreenUiState()
    data class Success(val article: Article) : ArticleDetailsScreenUiState()
    data class Error(val message: String) : ArticleDetailsScreenUiState()
}
