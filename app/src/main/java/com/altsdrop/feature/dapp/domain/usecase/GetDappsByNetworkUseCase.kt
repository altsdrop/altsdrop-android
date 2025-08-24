package com.altsdrop.feature.dapp.domain.usecase

import com.altsdrop.feature.dapp.domain.model.Dapp
import com.altsdrop.feature.dapp.domain.repository.DappRepository
import javax.inject.Inject

class GetDappsByNetworkUseCase @Inject constructor(
    private val repository: DappRepository
) {
    suspend operator fun invoke(network: String): List<Dapp> {
        return if (network == "All") {
            repository.getAllDapps()
        } else {
            repository.getDappsByNetwork(network)
        }
    }
}