package com.altsdrop.feature.dapp.domain.usecase

import com.altsdrop.feature.dapp.domain.model.Dapp
import com.altsdrop.feature.dapp.domain.repository.DappRepository
import javax.inject.Inject

class GetDappsByChainUseCase @Inject constructor(
    private val repository: DappRepository
) {
    suspend operator fun invoke(chain: String): List<Dapp> {
        return if (chain == "All") {
            repository.getAllDapps()
        } else {
            repository.getDappsByChain(chain)
        }
    }
}