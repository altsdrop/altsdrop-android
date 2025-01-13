package com.altsdrop.feature.news.data.model

import com.altsdrop.feature.news.domain.model.Article
import com.altsdrop.feature.news.util.convertFirebaseTimestamp
import com.google.firebase.Timestamp

data class ArticleDTO(
    val headerImage: String = "",
    val category: String = "",
    val content: String = "",
    val publishedDateTime: Timestamp = Timestamp.now(),
    val tags: List<String> = emptyList(),
    val title: String = "",
    val slug: String = "",
    val readTime: Int = 0
)

fun ArticleDTO.toDomain() = Article(
    headerImage = headerImage,
    category = category,
    content = content,
    publishedDateTime = convertFirebaseTimestamp(publishedDateTime),
    tags = tags,
    title = title,
    slug = slug,
    readTime = readTime
)