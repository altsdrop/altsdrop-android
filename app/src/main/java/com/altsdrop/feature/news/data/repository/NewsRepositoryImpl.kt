package com.altsdrop.feature.news.data.repository

import com.altsdrop.feature.news.data.model.ArticleDTO
import com.altsdrop.feature.news.data.model.toDomain
import com.altsdrop.feature.news.domain.model.Article
import com.altsdrop.feature.news.domain.repository.NewsRepository
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class NewsRepositoryImpl(
    private val newsCollectionRef: CollectionReference
) : NewsRepository {
    override suspend fun getArticles(): List<Article> = withContext(Dispatchers.IO) {
        return@withContext try {
            // Retrieve all documents in the collection
            val documents = newsCollectionRef.get().await()
            val articles = documents.toObjects(ArticleDTO::class.java)
            articles.map { it.toDomain() }
        } catch (e: Exception) {
            // Handle exception, e.g., logging or returning an empty list
            e.printStackTrace()
            emptyList()
        }
    }
}