package com.altsdrop.feature.dapp.domain.model

data class Dapp(
    val id: String,
    val name: String,
    val description: String,
    val iconUrl: String,
    val isIconUrlSvg: Boolean = false,
    val url: String,
    val tags: List<String>,
    val chains: List<String>,
    val isArchived: Boolean,
    val isFeatured: Boolean,
    val isHighlyRated: Boolean
)