package com.altsdrop.feature.news.data.repository

import com.altsdrop.feature.news.data.model.ArticleDTO
import com.altsdrop.feature.news.data.model.toDomain
import com.altsdrop.feature.news.domain.model.Article
import com.altsdrop.feature.news.domain.repository.NewsRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

private const val PUBLISHED_DATE_TIME_KEY = "publishedDateTime"
private const val IS_ARCHIVED_KEY = "isArchived"

class NewsRepositoryImpl(
    private val newsCollectionRef: CollectionReference
) : NewsRepository {
    override suspend fun getArticles(): List<Article> = withContext(Dispatchers.IO) {
        return@withContext try {
            // Retrieve all documents in the collection
            val documents = newsCollectionRef
                .whereEqualTo(IS_ARCHIVED_KEY, false)
                .orderBy(PUBLISHED_DATE_TIME_KEY, Query.Direction.DESCENDING)
                .get().await()
            val articles = documents.toObjects(ArticleDTO::class.java)
            articles.map { it.toDomain() }
        } catch (e: Exception) {
            // Handle exception, e.g., logging or returning an empty list
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getArticleBySlug(slug: String) = withContext(Dispatchers.IO) {
        return@withContext try {
            val snapshot = newsCollectionRef
                .document(slug)
                .get()
                .await()

            if (snapshot.exists()) {
                val dto = snapshot.toObject(ArticleDTO::class.java)
                if (dto != null) {
                    Result.success(dto.toDomain())
                } else {
                    Result.failure(NullPointerException("ArticleDTO is null"))
                }
            } else {
                Result.failure(NoSuchElementException("No document found with slug: $slug"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}