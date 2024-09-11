package com.altsdrop.feature.settings.data.repository

import com.altsdrop.feature.settings.data.model.toUser
import com.altsdrop.feature.settings.domain.model.User
import com.altsdrop.feature.settings.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : UserRepository {
    override suspend fun getUser(): User = withContext(Dispatchers.IO) {
        firebaseAuth.currentUser?.toUser() ?: User()
    }
}