package com.altsdrop.feature.settings.data.model

import com.squareup.moshi.JsonClass
import kotlinx.serialization.SerialName

@JsonClass(generateAdapter = true)
data class RemoteSettingsDTO(
    @SerialName("community")
    val community: List<LinkDTO> = emptyList(),
    @SerialName("about")
    val about: List<LinkDTO> = emptyList(),
    @SerialName("support")
    val support: List<LinkDTO> = emptyList(),
)
