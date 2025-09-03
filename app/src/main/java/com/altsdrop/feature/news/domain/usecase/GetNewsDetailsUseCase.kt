package com.altsdrop.feature.news.domain.usecase

import com.altsdrop.feature.news.domain.model.News
import com.altsdrop.feature.news.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsDetailsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(slug: String): Result<News> {
        return repository.getNewsBySlug(slug)
    }
}