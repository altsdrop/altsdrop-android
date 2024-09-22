package com.altsdrop.feature.settings.domain.model

import com.google.firebase.Timestamp

data class Feedback(
    val message: String,
    val timestamp: Timestamp,
    val email: String,
)
