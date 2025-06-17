package com.altsdrop.feature.news.domain.usecase

import com.altsdrop.feature.news.domain.model.Article
import com.altsdrop.feature.news.domain.repository.NewsRepository
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(): List<Article> {
        return repository.getArticles()
    }
}