package com.altsdrop.feature.news.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altsdrop.R
import com.altsdrop.core.util.Resources
import com.altsdrop.feature.news.domain.usecase.GetArticleDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    private val resources: Resources,
    private val getAirdropDetailsUseCase: GetArticleDetailsUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ArticleDetailsScreenUiState>(ArticleDetailsScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getArticleDetails(slug: String) {
        viewModelScope.launch {
            getAirdropDetailsUseCase(slug).fold(
                onSuccess = { airdrop ->
                    _uiState.value = ArticleDetailsScreenUiState.Success(
                        article = airdrop
                    )
                },
                onFailure = {
                    _uiState.value = ArticleDetailsScreenUiState.Error(
                        resources.getString(
                            R.string.error_airdrop_not_found
                        )
                    )
                }
            )
        }
    }
}