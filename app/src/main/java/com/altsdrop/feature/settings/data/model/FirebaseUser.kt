package com.altsdrop.feature.settings.data.model

import com.altsdrop.feature.settings.domain.model.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toUser(): User {
    return User(
        id = uid,
        name = displayName ?: "",
        email = email ?: "",
        photoUrl = photoUrl?.toString() ?: "",
    )
}
