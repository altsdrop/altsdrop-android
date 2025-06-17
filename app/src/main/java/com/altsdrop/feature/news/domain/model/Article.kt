package com.altsdrop.feature.news.domain.model

data class Article(
    val headerImage: String = "",
    val category: String = "",
    val content: String = "",
    val publishedDateTime: String = "",
    val tags: List<String> = emptyList(),
    val title: String = "",
    val slug: String = "",
    val readTime: Int = 0
)

val previewArticle = Article(
    headerImage = "https://picsum.photos/200",
    category = "Category 1",
    title = "Example Article Title",
    tags = listOf("ETH", "Bitcoin"),
    publishedDateTime = "21-May-2024",
    readTime = 5,
    content = "<p>In this post, we'll explore how to create a structured JSON response for blog posts...</p><img src='https://example.com/images/post-image1.jpg' alt='Example image' /><p>This is how images can be embedded in the body content.</p>"
)