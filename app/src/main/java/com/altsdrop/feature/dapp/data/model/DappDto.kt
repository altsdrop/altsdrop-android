package com.altsdrop.feature.dapp.data.model

import com.altsdrop.feature.dapp.domain.model.Dapp

data class DappDto(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val iconUrl: String = "",
    val url: String = "",
    val tags: List<String> = emptyList(),
    val network: String = ""
)

fun DappDto.toDomain() = Dapp(
    id = id,
    name = name,
    description = description,
    iconUrl = iconUrl,
    isIconUrlSvg = iconUrl.endsWith(".svg", ignoreCase = true),
    url = url,
    tags = tags,
    network = network
)