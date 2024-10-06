package com.altsdrop.feature.airdrop.data.model

import com.altsdrop.feature.airdrop.domain.model.Airdrop
import java.util.UUID

data class AirdropDTO(
    val title: String = "",
    val categories: String = "",
    val chain: String = "",
    val description: String = "",
    val endDate: String = "",
    val networks: List<String> = emptyList(),
    val officialLinks: List<HyperLinkDTO> = emptyList(),
    val shortDescription: String = "",
    val slug: String = "",
    val socialLinks: List<HyperLinkDTO> = emptyList(),
    val startDate: String = "",
    val steps: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val thumbnail: String = ""
)

fun AirdropDTO.toDomain(): Airdrop {
    return Airdrop(
        airdropId = UUID.randomUUID().toString(),
        title = title,
        categories = categories,
        chain = chain,
        description = description,
        endDate = endDate,
        networks = networks,
        officialLinks = officialLinks.map { it.toDomain() },
        shortDescription = shortDescription,
        slug = slug,
        socialLinks = socialLinks.map { it.toDomain() },
        startDate = startDate,
        steps = steps,
        tags = tags,
        thumbnail = thumbnail
    )
}

