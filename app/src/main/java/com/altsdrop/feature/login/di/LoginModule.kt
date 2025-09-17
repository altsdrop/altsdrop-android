package com.altsdrop.feature.login.di

import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.altsdrop.feature.login.data.repository.LoginRepositoryImpl
import com.altsdrop.feature.login.domain.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object LoginModule {

    @Provides
    @ViewModelScoped
    fun provideLoginRepository(
        getCredentialRequest: GetCredentialRequest,
        firestoreAuth: FirebaseAuth,
        credentialManager: CredentialManager,
    ): LoginRepository {
        return LoginRepositoryImpl(
            getCredentialRequest,
            firestoreAuth,
            credentialManager,
        )
    }
}