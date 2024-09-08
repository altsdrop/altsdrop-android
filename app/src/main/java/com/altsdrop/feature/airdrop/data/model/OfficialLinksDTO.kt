package com.altsdrop.feature.airdrop.data.model

import com.altsdrop.feature.airdrop.domain.model.OfficialLinks

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