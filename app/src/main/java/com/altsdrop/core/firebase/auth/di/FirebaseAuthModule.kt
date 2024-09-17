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

//    @Provides
//    @Named("signUpRequest")
//    fun provideSignUpRequest(
//        app: Application
//    ) = BeginSignInRequest.builder()
//        .setGoogleIdTokenRequestOptions(
//            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                .setSupported(true)
//                // Your server's client ID, not your Android client ID.
//                .setServerClientId(getString(R.string.your_web_client_id))
//                // Only show accounts previously used to sign in.
//                .setFilterByAuthorizedAccounts(true)
//                .build())
//        .build()
}