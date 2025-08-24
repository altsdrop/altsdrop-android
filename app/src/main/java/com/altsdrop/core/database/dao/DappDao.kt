package com.altsdrop.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.altsdrop.core.database.model.DappEntity

@Dao
interface DappDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(dapps: List<DappEntity>)

    @Query("SELECT * FROM dapps")
    fun getAll(): List<DappEntity>

    @Query(
        """
            SELECT * FROM dapps
            WHERE name LIKE '%' || :query || '%'
            OR url LIKE '%' || :query || '%'
        """
    )
    suspend fun searchDapps(query: String): List<DappEntity>

    @Query("SELECT * FROM dapps WHERE network = :network")
    suspend fun getDappsByNetwork(network: String): List<DappEntity>

    @Query("DELETE FROM dapps")
    fun deleteAll()
}