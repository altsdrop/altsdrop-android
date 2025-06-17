package com.altsdrop.feature.settings.domain.model

import java.util.UUID

data class SettingsCategory(
    val id: String = UUID.randomUUID().toString(),
    val categoryName: String,
    val settings: List<Setting> = emptyList(), // List of sub-settings for this category
    val isExpanded: Boolean = false // Whether the category is expanded in the UI
)