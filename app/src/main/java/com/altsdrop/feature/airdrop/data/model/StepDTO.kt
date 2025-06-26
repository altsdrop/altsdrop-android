package com.altsdrop.feature.airdrop.data.model

import com.altsdrop.feature.airdrop.domain.model.Step

data class StepDTO(
    val title: String = "",
    val content: String = "",
)

fun StepDTO.toDomain() = Step(
    title = title,
    content = content
)