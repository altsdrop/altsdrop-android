package com.altsdrop.feature.airdrop.domain.usecase

import com.altsdrop.feature.airdrop.domain.model.Airdrop
import com.altsdrop.feature.airdrop.domain.repository.AirdropRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFeaturedAirdropsUseCase @Inject constructor(
    private val repository: AirdropRepository
) {
    suspend operator fun invoke(): List<Airdrop> {
        return repository.getFeaturedAirdrops()
    }
}