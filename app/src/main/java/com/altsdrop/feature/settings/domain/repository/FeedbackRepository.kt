package com.altsdrop.feature.settings.domain.repository

import com.altsdrop.feature.settings.domain.model.Feedback
import com.google.firebase.firestore.DocumentReference

interface FeedbackRepository {
    suspend fun submitFeedback(feedback: Feedback): Result<DocumentReference>
}