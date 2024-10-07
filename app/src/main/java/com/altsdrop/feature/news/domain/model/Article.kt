package com.altsdrop.feature.news.domain.model

data class Article(
    val id: String = "",
    val headerImage: String = "",
    val categories: List<String> = emptyList(),
    val content: String = "",
    val publishedDate: String = "",
    val tags: List<String> = emptyList(),
    val title: String = "",
    val slug: String = ""
)

val previewArticle = Article(
    id = "1",
    headerImage = "https://picsum.photos/200",
    categories = listOf("Category 1", "Category 2"),
    title = "Example Article Title",
    tags = listOf("ETH", "Bitcoin"),
    publishedDate = "21-May-2024",
    content = "<p>In this post, we'll explore how to create a structured JSON response for blog posts...</p><img src='https://example.com/images/post-image1.jpg' alt='Example image' /><p>This is how images can be embedded in the body content.</p>"
)