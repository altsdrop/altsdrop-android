package com.altsdrop.feature.news.data.model

import com.altsdrop.feature.news.domain.model.Article
import com.altsdrop.feature.news.util.convertFirebaseTimestamp
import com.google.firebase.Timestamp

data class ArticleDTO(
    val id: String = "",
    val headerImage: String = "",
    val categories: List<String> = emptyList(),
    val content: String = "",
    val publishedDate: Timestamp = Timestamp.now(),
    val tags: List<String> = emptyList(),
    val title: String = "",
    val slug: String = "",
    val readingTime: Int = 0
)

fun ArticleDTO.toDomain() = Article(
    id = id,
    headerImage = headerImage,
    categories = categories,
    content = content,
    publishedDate = convertFirebaseTimestamp(publishedDate),
    tags = tags,
    title = title,
    slug = slug,
    readingTime = readingTime
)