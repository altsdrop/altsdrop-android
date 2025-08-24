package com.altsdrop.feature.dapp.domain.usecase

import com.altsdrop.feature.dapp.domain.model.Dapp
import com.altsdrop.feature.dapp.domain.repository.DappRepository
import javax.inject.Inject

class SearchDappsByNameOrUrlUseCase @Inject constructor(
    private val repository: DappRepository
) {
    suspend operator fun invoke(query: String): List<Dapp> {
        return repository.searchDappsByNameOrUrl(query)
    }
}