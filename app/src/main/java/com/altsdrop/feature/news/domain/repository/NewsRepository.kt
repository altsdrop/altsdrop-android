package com.altsdrop.feature.news.domain.repository

import com.altsdrop.feature.news.domain.model.News

interface NewsRepository {
    suspend fun getNews(): List<News>
    suspend fun getNewsBySlug(slug: String): Result<News>
}
