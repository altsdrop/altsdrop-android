package com.altsdrop.feature.news.data.model

import com.altsdrop.feature.news.domain.model.Article
import com.google.firebase.Timestamp

data class ArticleDTO(
    val id: String = "",
    val headerImage: String = "",
    val category: String = "",
    val content: String = "",
    val publishedDate: Timestamp = Timestamp.now(),
    val tags: List<String> = emptyList(),
    val title: String = ""
)

fun ArticleDTO.toDomain() = Article(
    id = id,
    headerImage = headerImage,
    category = category,
    content = content,
    publishedDate = publishedDate,
    tags = tags,
    title = title
)