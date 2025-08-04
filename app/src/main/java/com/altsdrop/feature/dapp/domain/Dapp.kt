package com.altsdrop.feature.dapp.domain

data class Dapp(
    val name: String,
    val description: String,
    val iconUrl: String,
    val url: String,
    val tags: List<String>,
    val network: String
)
