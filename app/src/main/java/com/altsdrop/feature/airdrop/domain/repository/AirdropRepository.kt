package com.altsdrop.feature.airdrop.domain.repository

import com.altsdrop.feature.airdrop.domain.model.Airdrop

interface AirdropRepository {
    suspend fun getFeaturedAirdrops(): List<Airdrop>
}