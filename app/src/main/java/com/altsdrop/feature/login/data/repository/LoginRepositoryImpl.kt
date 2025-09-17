package com.altsdrop.feature.login.data.repository

import android.content.Context
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.altsdrop.feature.login.domain.repository.LoginRepository
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val getCredentialRequest: GetCredentialRequest,
    private val firestoreAuth: FirebaseAuth,
    private val credentialManager: CredentialManager,
) : LoginRepository {
    override suspend fun googleSignIn(context: Context): Result<Boolean> =
        withContext(Dispatchers.IO) {
            try {
                val credential = getCredentialFromCredentialManager(
                    context = context
                )
                if (credential is CustomCredential &&
                    credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                ) {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
                    val googleIdToken = googleIdTokenCredential.idToken
                    signInWithGoogle(googleIdToken)
                    Result.success(true)
                } else {
                    Result.failure(Exception("Unexpected Credential Type"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    private suspend fun signInWithGoogle(idToken: String) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        firestoreAuth.signInWithCredential(firebaseCredential).await()
    }

    private suspend fun getCredentialFromCredentialManager(context: Context): Credential? {
        val contextRef = WeakReference(context).get()
            ?: throw IllegalStateException("Context is no longer available.")

        return try {
            credentialManager.getCredential(
                request = getCredentialRequest,
                context = contextRef
            ).credential
        } catch (exception: GetCredentialException) {
            throw exception
        }
    }
}