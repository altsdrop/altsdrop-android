package com.altsdrop.feature.settings.data.repository

import com.altsdrop.core.firebase.firestore.util.getSafeResult
import com.altsdrop.feature.settings.data.model.toBugReportDTO
import com.altsdrop.feature.settings.domain.model.BugReport
import com.altsdrop.feature.settings.domain.repository.BugReportRepository
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class BugReportRepositoryImpl(
    private val bugReportCollectionRef: CollectionReference
) : BugReportRepository {

    override suspend fun submitBugReport(bugReport: BugReport) = withContext(Dispatchers.IO) {
        val bugReportDTO = bugReport.toBugReportDTO()

        getSafeResult {
            bugReportCollectionRef.add(bugReportDTO).await()
        }
    }
}
