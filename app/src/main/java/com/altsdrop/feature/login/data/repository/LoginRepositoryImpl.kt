package com.altsdrop.feature.login.data.repository

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.altsdrop.feature.login.domain.repository.LoginRepository
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val context: Context,
    private val getSignInWithGoogleOption: GetSignInWithGoogleOption,
    private val firestoreAuth: FirebaseAuth,
    private val credentialManager: CredentialManager,
) : LoginRepository {
    override suspend fun googleSignIn(): Result<Boolean> = withContext(Dispatchers.IO) {
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(getSignInWithGoogleOption)
            .build()

        return@withContext try {
            val result = credentialManager.getCredential(
                request = request,
                context = context,
            )

            val getGoogleIdToken = handleSignIn(result)
            if (getGoogleIdToken != null) {
                val firebaseCredential = GoogleAuthProvider.getCredential(getGoogleIdToken, null)
                val credential = firestoreAuth.signInWithCredential(firebaseCredential).await()
                if (credential.user != null) {
                    Result.success(true)
                } else {
                    Result.failure(Exception("Failed to login"))
                }
            } else {
                Result.failure(Exception("Failed to login"))
            }
        } catch (e: GetCredentialException) {
            Result.failure(Exception("Failed to login"))
        }
    }

    private fun handleSignIn(result: GetCredentialResponse): String? {
        // Handle the successfully returned credential.
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)

                        return googleIdTokenCredential.idToken
                    } catch (e: GoogleIdTokenParsingException) {
                        return null
                    }
                }
                else {
                    // Catch any unrecognized credential type here.
                    return null
                }
            }
            else -> {
                // Catch any unrecognized credential type here.
                return null
            }
        }
    }

}