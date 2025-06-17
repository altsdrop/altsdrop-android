package com.altsdrop.feature.login.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.altsdrop.feature.login.data.repository.LoginRepositoryImpl
import com.altsdrop.feature.login.domain.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object LoginModule {

    @Provides
    fun provideLoginRepository(
        @ApplicationContext context: Context,
        getCredentialRequest: GetCredentialRequest,
        firestoreAuth: FirebaseAuth,
        credentialManager: CredentialManager,
    ): LoginRepository {
        return LoginRepositoryImpl(
            context,
            getCredentialRequest,
            firestoreAuth,
            credentialManager,
        )
    }
}