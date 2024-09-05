package com.altsdrop.feature.airdrop.data.repository

import com.altsdrop.feature.airdrop.data.repository.model.AirdropDTO
import com.altsdrop.feature.airdrop.data.repository.model.toDomain
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
}