package com.altsdrop.feature.dapp.domain.repository

import com.altsdrop.feature.dapp.domain.model.Dapp

interface DappRepository {
    suspend fun getAllDapps(): List<Dapp>
    suspend fun getDappsByChain(chain: String): List<Dapp>
    suspend fun searchDappsByNameOrUrl(query: String): List<Dapp>
}