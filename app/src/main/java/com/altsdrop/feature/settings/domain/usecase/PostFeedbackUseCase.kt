package com.altsdrop.feature.settings.domain.usecase

import com.altsdrop.feature.settings.domain.model.Feedback
import com.altsdrop.feature.settings.domain.repository.FeedbackRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class PostFeedbackUseCase @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val feedbackRepository: FeedbackRepository
) {
    suspend operator fun invoke(feedbackText: String): Result<DocumentReference> {
        val user = getUserDetailsUseCase()
        val feedback = Feedback(
            message = feedbackText,
            timestamp = Timestamp.now(),
            email = user.email
        )
        return feedbackRepository.submitFeedback(feedback)
    }
}