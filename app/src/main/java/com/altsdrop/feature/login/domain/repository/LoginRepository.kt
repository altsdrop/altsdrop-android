package com.altsdrop.feature.login.domain.repository

interface LoginRepository {
    suspend fun googleSignIn(): Result<Boolean>
}