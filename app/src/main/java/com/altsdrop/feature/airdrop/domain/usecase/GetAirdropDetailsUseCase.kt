package com.altsdrop.feature.airdrop.domain.usecase

import com.altsdrop.feature.airdrop.domain.model.Airdrop
import com.altsdrop.feature.airdrop.domain.repository.AirdropRepository
import javax.inject.Inject

class GetAirdropDetailsUseCase @Inject constructor(
    private val repository: AirdropRepository
) {
    suspend operator fun invoke(slug: String): Result<Airdrop> {
        return repository.getAirdropDetails(slug)
    }
}