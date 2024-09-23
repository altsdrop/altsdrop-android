package com.altsdrop.feature.settings.data.model

import com.altsdrop.feature.settings.domain.model.BugReport
import com.altsdrop.feature.settings.domain.model.Feedback
import com.google.firebase.Timestamp

data class BugReportDTO(
    val message: String,
    val timestamp: Timestamp,
    val reportedBy: String,
)

fun BugReport.toBugReportDTO() = BugReportDTO(
    message = message,
    timestamp = timestamp,
    reportedBy = reportedBy,
)