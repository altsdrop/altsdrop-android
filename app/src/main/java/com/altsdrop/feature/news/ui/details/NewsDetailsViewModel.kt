package com.altsdrop.feature.news.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altsdrop.R
import com.altsdrop.core.util.Resources
import com.altsdrop.feature.news.domain.usecase.GetNewsDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    private val resources: Resources,
    private val getNewsDetailsUseCase: GetNewsDetailsUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<NewsDetailsScreenUiState>(NewsDetailsScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getNewsDetails(slug: String) {
        viewModelScope.launch {
            getNewsDetailsUseCase(slug).fold(
                onSuccess = { news ->
                    _uiState.value = NewsDetailsScreenUiState.Success(
                        news = news
                    )
                },
                onFailure = {
                    _uiState.value = NewsDetailsScreenUiState.Error(
                        resources.getString(
                            R.string.error_news_not_found
                        )
                    )
                }
            )
        }
    }
}