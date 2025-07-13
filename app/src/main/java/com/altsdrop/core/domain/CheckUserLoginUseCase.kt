package com.altsdrop.core.domain

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckUserLoginUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend operator fun invoke(): Boolean = withContext(Dispatchers.IO) {
        if (firebaseAuth.currentUser == null) {
            return@withContext false
        }

        try {
            Tasks.await((firebaseAuth.currentUser!!.getIdToken(true)))
            true
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            FirebaseAuth.getInstance().signOut()
            false
        }
    }
}