package com.altsdrop.feature.login.ui

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altsdrop.core.domain.CheckUserLoginUseCase
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getSignInWithGoogleOption: GetSignInWithGoogleOption,
    private val userLoginUseCase: CheckUserLoginUseCase,
    private val firestoreAuth: FirebaseAuth,
    private val credentialManager: CredentialManager, @ApplicationContext private val context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun onSignInWithGoogleClick() {
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(getSignInWithGoogleOption)
            .build()

        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                Log.e("", e.toString())
            }
        }
    }

    fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        val credential = result.credential

        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)

                        when {
                            googleIdTokenCredential.idToken != null -> {
                                // Got an ID token from Google. Use it to authenticate
                                // with Firebase.
                                val firebaseCredential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                                val result = firestoreAuth.signInWithCredential(firebaseCredential)
                                if(result.isSuccessful){
                                    Log.d("", "Successfully logged in")
                                    val user = Firebase.auth.currentUser
                                    println(user?.email)
                                } else {
                                    Log.d("", "Failed to login")
                                }
                            }
                            else -> {
                                // Shouldn't happen.
                                Log.d("", "No ID token!")
                            }
                        }
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("", "Received an invalid google id token response", e)
                    }
                }
//                else -> {
//                    // Catch any unrecognized credential type here.
//                    Log.e(TAG, "Unexpected type of credential")
//                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e("", "Unexpected type of credential")
            }
        }
    }
}