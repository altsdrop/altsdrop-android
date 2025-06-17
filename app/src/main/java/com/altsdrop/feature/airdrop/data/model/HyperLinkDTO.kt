package com.altsdrop.feature.airdrop.data.model

import com.altsdrop.feature.airdrop.domain.model.HyperLink

data class HyperLinkDTO(
    val title: String = "",
    val url: String = "",
)

fun HyperLinkDTO.toDomain() = HyperLink(
    url = url,
    title = title
)