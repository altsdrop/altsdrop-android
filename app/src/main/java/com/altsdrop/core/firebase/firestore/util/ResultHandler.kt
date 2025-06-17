package com.altsdrop.core.firebase.firestore.util

suspend fun <T> getSafeResult(block: suspend () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (e: Exception) {
        Result.failure(e)
    }
}