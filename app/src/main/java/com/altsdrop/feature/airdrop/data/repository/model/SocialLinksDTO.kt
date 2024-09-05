package com.altsdrop.feature.airdrop.data.repository.model

import com.altsdrop.feature.airdrop.domain.SocialLinks

data class SocialLinksDTO(
    val discord: String = "",
    val x: String = "",
)

fun SocialLinksDTO.toDomain() = SocialLinks(
    discord = discord,
    x = x,
)
