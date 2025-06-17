package com.altsdrop.feature.settings.domain.repository

import com.altsdrop.feature.settings.domain.model.User

interface UserRepository {
    suspend fun getUser(): User
}