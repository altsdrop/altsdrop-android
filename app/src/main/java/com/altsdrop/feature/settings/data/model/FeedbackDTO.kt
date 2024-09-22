package com.altsdrop.feature.settings.data.model

import com.altsdrop.feature.settings.domain.model.Feedback
import com.google.firebase.Timestamp

data class FeedbackDTO(
    val message: String,
    val timestamp: Timestamp,
    val email: String,
)

fun Feedback.toFeedbackDTO() = FeedbackDTO(
    message = message,
    timestamp = timestamp,
    email = email
)