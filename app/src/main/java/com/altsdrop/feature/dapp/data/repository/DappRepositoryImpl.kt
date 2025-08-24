package com.altsdrop.feature.dapp.data.repository

import com.altsdrop.core.database.dao.DappDao
import com.altsdrop.core.database.model.toDomain
import com.altsdrop.core.database.model.toEntity
import com.altsdrop.feature.dapp.data.model.DappDto
import com.altsdrop.feature.dapp.data.model.toDomain
import com.altsdrop.feature.dapp.domain.model.Dapp
import com.altsdrop.feature.dapp.domain.repository.DappRepository
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DappRepositoryImpl(
    private val dappCollectionRef: CollectionReference,
    private val dappDao: DappDao
) : DappRepository {
    override suspend fun getAllDapps(): List<Dapp> = withContext(Dispatchers.IO) {
        val localDapps = dappDao.getAll().map { it.toDomain() }

        if (localDapps.isNotEmpty()) {
            return@withContext localDapps
        }

        // If empty, fetch from Firestore
        return@withContext try {
            val documents = dappCollectionRef
                .get()
                .await()

            val dapps = documents.toObjects(DappDto::class.java).map { it.toDomain() }

            // cache in Room
            if (dapps.isNotEmpty()) {
                dappDao.insertAll(dapps.map { it.toEntity() })
            }

            dapps
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getDappsByNetwork(network: String): List<Dapp> =
        withContext(Dispatchers.IO) {
            dappDao.getDappsByNetwork(network).map {
                it.toDomain()
            }
        }

    override suspend fun searchDappsByNameOrUrl(query: String): List<Dapp> =
        withContext(Dispatchers.IO) {
            dappDao.searchDapps(query).map {
                it.toDomain()
            }
        }
}