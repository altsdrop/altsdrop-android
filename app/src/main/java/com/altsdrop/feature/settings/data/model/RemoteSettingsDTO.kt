package com.altsdrop.feature.settings.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteSettingsDTO(
    @Json(name = "community")
    val community: List<LinkDTO> = emptyList(),
    @Json(name = "about")
    val about: List<LinkDTO> = emptyList(),
    @Json(name = "support")
    val support: List<LinkDTO> = emptyList()
)
