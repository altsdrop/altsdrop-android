package com.altsdrop.feature.airdrop.data.repository

import com.altsdrop.feature.airdrop.data.model.AirdropDTO
import com.altsdrop.feature.airdrop.data.model.toDomain
import com.altsdrop.feature.airdrop.domain.model.Airdrop
import com.altsdrop.feature.airdrop.domain.repository.AirdropRepository
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AirdropRepositoryImpl(
    private val airdropCollectionRef: CollectionReference
) : AirdropRepository {
    override suspend fun getFeaturedAirdrops(): List<Airdrop> = withContext(Dispatchers.IO) {
        return@withContext try {
            // Retrieve all documents in the collection
            val documents = airdropCollectionRef.get().await()
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
            // Retrieve all documents in the collection
            val document = airdropCollectionRef
                .whereEqualTo("slug", slug)
                .get()
                .await()
            val airdropDTOs = document.toObjects(AirdropDTO::class.java)
            Result.success(airdropDTOs.first().toDomain())
        } catch (e: Exception) {
            // Handle exception, e.g., logging or returning an empty list
            e.printStackTrace()
            Result.failure(e)
        }
    }
}