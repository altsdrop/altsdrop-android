package com.altsdrop.feature.airdrop.data.model

import com.altsdrop.feature.airdrop.domain.model.Airdrop
import com.google.firebase.Timestamp
import java.util.UUID

data class AirdropDTO(
    val title: String = "",
    val categories: String = "",
    val chain: String = "",
    val description: String = "",
    val isHighlyRated: Boolean = false,
    val isFeatured: Boolean = false,
    val dateAdded: Timestamp = Timestamp.now(),
    val endDate: Timestamp = Timestamp.now(),
    val networks: List<String> = emptyList(),
    val officialLinks: List<HyperLinkDTO> = emptyList(),
    val shortDescription: String = "",
    val slug: String = "",
    val socialLinks: List<HyperLinkDTO> = emptyList(),
    val startDate: Timestamp = Timestamp.now(),
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
        isHighlyRated = isHighlyRated,
        isFeatured = isFeatured,
        dateAdded = dateAdded.toString(),
        endDate = endDate.toString(),
        networks = networks,
        officialLinks = officialLinks.map { it.toDomain() },
        shortDescription = shortDescription,
        slug = slug,
        socialLinks = socialLinks.map { it.toDomain() },
        startDate = startDate.toString(),
        steps = steps,
        tags = tags,
        thumbnail = thumbnail
    )
}

