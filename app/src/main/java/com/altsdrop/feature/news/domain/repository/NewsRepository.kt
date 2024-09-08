package com.altsdrop.feature.news.domain.repository

import com.altsdrop.feature.news.domain.model.Article

interface NewsRepository {
    suspend fun getArticles(): List<Article>
}
