package com.altsdrop.feature.airdrop.data.repository

import com.altsdrop.feature.airdrop.data.model.AirdropDTO
import com.altsdrop.feature.airdrop.data.model.toDomain
import com.altsdrop.feature.airdrop.domain.model.Airdrop
import com.altsdrop.feature.airdrop.domain.repository.AirdropRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AirdropRepositoryImpl(
    private val airdropCollectionRef: CollectionReference
) : AirdropRepository {
    override suspend fun getFeaturedAirdrops(): List<Airdrop> = withContext(Dispatchers.IO) {
        return@withContext try {
            // Retrieve all documents in the collection
            val documents = airdropCollectionRef
                .whereEqualTo("isFeatured", true)
                .whereEqualTo("isArchived", false)
                .orderBy("dateAdded", Query.Direction.DESCENDING)
                .limit(5)
                .get()
                .await()

            val airdropDTOs = documents.toObjects(AirdropDTO::class.java)

            airdropDTOs.map { it.toDomain() }
        } catch (e: Exception) {
            // Handle exception, e.g., logging or returning an empty list
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getNewAirdrops(): List<Airdrop> = withContext(Dispatchers.IO) {
        return@withContext try {
            // Retrieve all new documents in the collection
            val documents = airdropCollectionRef
                .whereEqualTo("isFeatured", false)
                .whereEqualTo("isArchived", false)
                .orderBy("dateAdded", Query.Direction.DESCENDING)
                .get()
                .await()

            val airdropDTOs = documents.toObjects(AirdropDTO::class.java)

            airdropDTOs.map { it.toDomain() }
        } catch (e: Exception) {
            // Handle exception, e.g., logging or returning an empty list
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getExploreAirdrops(): List<Airdrop> = withContext(Dispatchers.IO) {
        return@withContext try {
            // Retrieve all new documents in the collection
            val documents = airdropCollectionRef
                .whereEqualTo("isArchived", false)
                .get()
                .await()

            val airdropDTOs = documents.toObjects(AirdropDTO::class.java)

            airdropDTOs.map { it.toDomain() }
        } catch (e: Exception) {
            // Handle exception, e.g., logging or returning an empty list
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getHighlyRatedAirdrops(): List<Airdrop> = withContext(Dispatchers.IO) {
        return@withContext try {
            // Retrieve all new documents in the collection
            val documents = airdropCollectionRef
                .whereEqualTo("isHighlyRated", true)
                .whereEqualTo("isArchived", false)
                .orderBy("dateAdded", Query.Direction.DESCENDING)
                .get()
                .await()

            val airdropDTOs = documents.toObjects(AirdropDTO::class.java)

            airdropDTOs.map { it.toDomain() }
        } catch (e: Exception) {
            // Handle exception, e.g., logging or returning an empty list
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getAirdropDetails(slug: String) = withContext(Dispatchers.IO) {
        return@withContext try {
            // Directly fetch the document by ID
            val snapshot = airdropCollectionRef
                .document(slug)
                .get()
                .await()

            if (snapshot.exists()) {
                val dto = snapshot.toObject(AirdropDTO::class.java)
                if (dto != null) {
                    Result.success(dto.toDomain())
                } else {
                    Result.failure(NullPointerException("AirdropDTO is null"))
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