package com.altsdrop.feature.airdrop.data.repository.model

import com.altsdrop.feature.airdrop.domain.OfficialLinks

data class OfficialLinksDTO(
    val website: String = "",
    val whitePaper: String = "",
    val documentation: String = "",
)

fun OfficialLinksDTO.toDomain() = OfficialLinks(
    website = website,
    whitePaper = whitePaper,
    documentation = documentation,
)