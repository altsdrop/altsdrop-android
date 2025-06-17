package com.altsdrop.feature.airdrop.domain.repository

import com.altsdrop.feature.airdrop.domain.model.Airdrop

interface AirdropRepository {
    suspend fun getFeaturedAirdrops(): List<Airdrop>
    suspend fun getNewAirdrops(): List<Airdrop>
    suspend fun getExploreAirdrops(): List<Airdrop>
    suspend fun getHighlyRatedAirdrops(): List<Airdrop>
    suspend fun getAirdropDetails(slug: String): Result<Airdrop>
}