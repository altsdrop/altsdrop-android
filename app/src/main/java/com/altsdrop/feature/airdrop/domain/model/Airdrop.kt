package com.altsdrop.feature.airdrop.domain.model

import java.util.UUID

data class Airdrop(
    val airdropId: String = UUID.randomUUID().toString(),
    val title: String = "",
    val categories: String = "",
    val chain: String = "",
    val description: String = "",
    val endDate: String = "",
    val networks: List<String> = listOf(""),
    val officialLinks: OfficialLinks = OfficialLinks(),
    val shortDescription: String = "",
    val slug: String = "",
    val socialLinks: SocialLinks = SocialLinks(),
    val startDate: String = "",
    val steps: List<String> = listOf(),
    val tags: List<String> = listOf(),
    val thumbnail: String = ""
)
