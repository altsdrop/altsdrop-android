package com.altsdrop.core.firebase.auth.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object FirebaseAuthModule {

    @Provides
    fun provideFirestoreAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    fun provideFirebaseUser(firestoreAuth: FirebaseAuth): FirebaseUser? {
        return firestoreAuth.currentUser
    }
}