package com.altsdrop.feature.news.domain.usecase

import com.altsdrop.feature.news.domain.model.Article
import com.altsdrop.feature.news.domain.repository.NewsRepository
import javax.inject.Inject

class GetArticleDetailsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(slug: String): Result<Article> {
        return repository.getArticleBySlug(slug)
    }
}