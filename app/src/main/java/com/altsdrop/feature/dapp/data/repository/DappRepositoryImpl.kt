package com.altsdrop.feature.dapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.altsdrop.core.database.dao.DappDao
import com.altsdrop.core.database.model.toDomain
import com.altsdrop.core.util.PreferencesKeys
import com.altsdrop.feature.dapp.data.model.DappDto
import com.altsdrop.feature.dapp.data.model.toDomain
import com.altsdrop.feature.dapp.data.model.toEntity
import com.altsdrop.feature.dapp.domain.model.Dapp
import com.altsdrop.feature.dapp.domain.repository.DappRepository
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DappRepositoryImpl(
    private val dappCollectionRef: CollectionReference,
    private val collectionMetaDataRef: CollectionReference,
    private val dappDao: DappDao,
    private val dataStore: DataStore<Preferences>
) : DappRepository {
    override suspend fun getAllDapps(): List<Dapp> = withContext(Dispatchers.IO) {
        val localLastUpdated = dataStore.data
            .map { preferences -> preferences[PreferencesKeys.DAPPS_LAST_UPDATE] ?: 0 }
            .first()

        val localDapps = dappDao.getAll().map { it.toDomain() }

        try {
            // Get collection-level lastUpdated from Firestore
            val metaDoc = collectionMetaDataRef.document("dapps").get().await()
            val remoteLastUpdated = metaDoc.getTimestamp("lastUpdated")?.toDate()?.time ?: 0L

            // Refresh from Firestore if local cache is empty or outdated
            if (localDapps.isEmpty() || remoteLastUpdated > localLastUpdated) {
                val documents = dappCollectionRef.get().await()
                val dapps = documents.toObjects(DappDto::class.java)

                // Cache in Room
                if (dapps.isNotEmpty()) {
                    dappDao.insertAll(dapps.map { it.toEntity() })
                    // Update lastUpdated in SharedPreferences
                    dataStore.updateData { prefs ->
                        prefs.toMutablePreferences().apply {
                            this[PreferencesKeys.DAPPS_LAST_UPDATE] = remoteLastUpdated
                        }
                    }
                }

                return@withContext dapps.map { it.toDomain() }
            } else {
                return@withContext localDapps
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext localDapps.ifEmpty { emptyList() }
        }
    }

    override suspend fun getDappsByChain(chain: String): List<Dapp> =
        withContext(Dispatchers.IO) {
            dappDao.getDappsByChain(chain).map {
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