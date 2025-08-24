package com.altsdrop.feature.dapp.domain.model

data class Dapp(
    val id: String,
    val name: String,
    val description: String,
    val iconUrl: String,
    val isIconUrlSvg: Boolean = false,
    val url: String,
    val tags: List<String>,
    val network: String
)