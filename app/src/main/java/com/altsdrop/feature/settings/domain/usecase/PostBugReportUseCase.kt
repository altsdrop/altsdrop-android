package com.altsdrop.feature.settings.domain.usecase

import com.altsdrop.core.domain.AppInfoProvider
import com.altsdrop.feature.settings.domain.model.BugReport
import com.altsdrop.feature.settings.domain.repository.BugReportRepository
import com.altsdrop.feature.settings.domain.repository.UserRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class PostBugReportUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val bugReportRepository: BugReportRepository,
    private val appInfoProvider: AppInfoProvider
) {
    suspend operator fun invoke(message: String): Result<DocumentReference> {
        val user = userRepository.getUser()
        val bugReport = BugReport(
            message = message,
            timestamp = Timestamp.now(),
            reportedBy = user.email,
            appVersionName = appInfoProvider.versionName,
            appVersionCode = appInfoProvider.versionCode
        )
        return bugReportRepository.submitBugReport(bugReport)
    }
}