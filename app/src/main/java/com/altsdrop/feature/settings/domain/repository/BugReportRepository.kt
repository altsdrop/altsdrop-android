package com.altsdrop.feature.settings.domain.repository

import com.altsdrop.feature.settings.domain.model.BugReport
import com.google.firebase.firestore.DocumentReference

interface BugReportRepository {
    suspend fun submitBugReport(bugReport: BugReport): Result<DocumentReference>
}