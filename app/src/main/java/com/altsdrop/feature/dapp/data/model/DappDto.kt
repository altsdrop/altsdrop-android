package com.altsdrop.feature.dapp.data.model

import com.altsdrop.core.database.model.DappEntity
import com.altsdrop.feature.dapp.domain.model.Dapp

data class DappDto(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val iconUrl: String = "",
    val url: String = "",
    val tags: List<String> = emptyList(),
    val chains: List<String> = emptyList(),
    @field:JvmField
    val isArchived: Boolean = false,
    @field:JvmField
    val isFeatured: Boolean = false,
    @field:JvmField
    val isHighlyRated: Boolean = false
)

fun DappDto.toDomain() = Dapp(
    id = id,
    name = name,
    description = description,
    iconUrl = iconUrl,
    isIconUrlSvg = iconUrl.endsWith(".svg", ignoreCase = true),
    url = url,
    tags = tags,
    chains = chains,
    isArchived = isArchived,
    isFeatured = isFeatured,
    isHighlyRated = isHighlyRated
)

fun DappDto.toEntity() = DappEntity(
    id = id,
    name = name,
    description = description,
    iconUrl = iconUrl,
    url = url,
    tags = tags,
    chains = chains,
    isArchived = isArchived,
    isFeatured = isFeatured,
    isHighlyRated = isHighlyRated
)