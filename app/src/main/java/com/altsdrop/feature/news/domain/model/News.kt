package com.altsdrop.feature.news.domain.model

data class News(
    val headerImage: String = "",
    val category: String = "",
    val content: String = "",
    val publishedDateTime: String = "",
    val tags: List<String> = emptyList(),
    val title: String = "",
    val slug: String = "",
    val readTime: Int = 0
)

val previewNews = News(
    headerImage = "https://picsum.photos/200",
    category = "Category 1",
    title = "Example News Title",
    tags = listOf("ETH", "Bitcoin"),
    publishedDateTime = "21-May-2024",
    readTime = 5,
    content = "<p>In this post, we'll explore how to create a structured JSON response for blog posts...</p><img src='https://example.com/images/post-image1.jpg' alt='Example image' /><p>This is how images can be embedded in the body content.</p>"
)