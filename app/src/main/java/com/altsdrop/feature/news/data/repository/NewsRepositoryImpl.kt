package com.altsdrop.feature.news.data.repository

import com.altsdrop.feature.news.data.model.NewsDto
import com.altsdrop.feature.news.data.model.toDomain
import com.altsdrop.feature.news.domain.model.News
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
    override suspend fun getNews(): List<News> = withContext(Dispatchers.IO) {
        return@withContext try {
            // Retrieve all documents in the collection
            val documents = newsCollectionRef
                .whereEqualTo(IS_ARCHIVED_KEY, false)
                .orderBy(PUBLISHED_DATE_TIME_KEY, Query.Direction.DESCENDING)
                .get().await()
            val news = documents.toObjects(NewsDto::class.java)
            news.map { it.toDomain() }
        } catch (e: Exception) {
            // Handle exception, e.g., logging or returning an empty list
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getNewsBySlug(slug: String) = withContext(Dispatchers.IO) {
        return@withContext try {
            val snapshot = newsCollectionRef
                .document(slug)
                .get()
                .await()

            if (snapshot.exists()) {
                val dto = snapshot.toObject(NewsDto::class.java)
                if (dto != null) {
                    Result.success(dto.toDomain())
                } else {
                    Result.failure(NullPointerException("NewsDto is null"))
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