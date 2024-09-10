package com.altsdrop.feature.news.domain.model

import com.google.firebase.Timestamp

data class Article(
    val id: String = "",
    val headerImage: String = "",
    val category: String = "",
    val content: String = "",
    val publishedDate: String = "",
    val tags: List<String> = emptyList(),
    val title: String = ""
)