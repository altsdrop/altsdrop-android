package com.altsdrop.feature.settings.domain.model

import com.google.firebase.Timestamp

data class BugReport(
    val message: String,
    val timestamp: Timestamp,
    val reportedBy: String,
)
