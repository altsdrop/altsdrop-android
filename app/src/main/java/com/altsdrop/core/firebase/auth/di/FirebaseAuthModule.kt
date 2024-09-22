package com.altsdrop.core.firebase.auth.di

import android.content.Context
import androidx.credentials.CredentialManager
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

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

    @Provides
    fun provideSignInWithGoogleOption() = GetSignInWithGoogleOption
        .Builder("827421645026-eqplc9gpikqb48joc2r2bmoev8o57e8e.apps.googleusercontent.com")
        .build()

    @Provides
    fun provideCredentialManager(
        @ApplicationContext context: Context
    ): CredentialManager = CredentialManager.create(context)
}