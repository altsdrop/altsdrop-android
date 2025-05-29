package com.altsdrop.feature.settings.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LinkDTO(
    val title: String,
    val url: String
)
