package com.altsdrop.feature.settings.data.model

import kotlinx.serialization.SerialName

data class RemoteSettingsDTO(
    @SerialName("community")
    val community: List<LinkDTO> = emptyList(),
    @SerialName("about")
    val about: List<LinkDTO> = emptyList(),
    @SerialName("support")
    val support: List<LinkDTO> = emptyList(),
)
