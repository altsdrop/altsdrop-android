package com.altsdrop.feature.settings.data.repository

import com.altsdrop.core.firebase.firestore.util.getSafeResult
import com.altsdrop.feature.settings.data.model.toFeedbackDTO
import com.altsdrop.feature.settings.domain.model.Feedback
import com.altsdrop.feature.settings.domain.repository.FeedbackRepository
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FeedbackRepositoryImpl(
    private val feedbackCollectionRef: CollectionReference
) : FeedbackRepository {

    override suspend fun submitFeedback(feedback: Feedback) = withContext(Dispatchers.IO) {
        val feedbackDTO = feedback.toFeedbackDTO()

        getSafeResult {
            feedbackCollectionRef.add(feedbackDTO).await()
        }
    }
}
