package com.altsdrop.feature.news.domain.usecase

import com.altsdrop.feature.news.domain.model.News
import com.altsdrop.feature.news.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(): List<News> {
        return repository.getNews()
    }
}