package com.altsdrop.feature.login.domain.repository

import android.content.Context

interface LoginRepository {
    suspend fun googleSignIn(context: Context): Result<Boolean>
}