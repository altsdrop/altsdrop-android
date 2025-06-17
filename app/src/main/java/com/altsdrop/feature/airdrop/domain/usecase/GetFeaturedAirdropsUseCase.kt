package com.altsdrop.feature.airdrop.domain.usecase

import com.altsdrop.feature.airdrop.domain.model.Airdrop
import com.altsdrop.feature.airdrop.domain.repository.AirdropRepository
import javax.inject.Inject

class GetFeaturedAirdropsUseCase @Inject constructor(
    private val repository: AirdropRepository
) {
    suspend operator fun invoke(): List<Airdrop> {
        return repository.getFeaturedAirdrops()
    }
}