package com.altsdrop.core.domain

import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class CheckUserLoginUseCase @Inject constructor(
    private val firebaseUser: FirebaseUser?
) {
    operator fun invoke(): Boolean {
        return firebaseUser != null
    }
}