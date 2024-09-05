package com.altsdrop.feature.airdrop.data.repository.model

import com.altsdrop.feature.airdrop.domain.model.Airdrop
import java.util.UUID

data class AirdropDTO(
    val title: String = "",
    val categories: String = "",
    val chain: String = "",
    val description: String = "",
    val endDate: String = "",
    val networks: List<String> = emptyList(),
    val officialLinks: OfficialLinksDTO = OfficialLinksDTO(),
    val shortDescription: String = "",
    val slug: String = "",
    val socialLinks: SocialLinksDTO = SocialLinksDTO(),
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
        officialLinks = officialLinks.toDomain(),
        shortDescription = shortDescription,
        slug = slug,
        socialLinks = socialLinks.toDomain(),
        startDate = startDate,
        steps = steps,
        tags = tags,
        thumbnail = thumbnail
    )
}

