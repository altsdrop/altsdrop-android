package com.altsdrop.feature.login.domain.usecase

import android.content.Context
import com.altsdrop.R
import com.altsdrop.core.util.Resources
import com.altsdrop.feature.login.domain.model.SignInState
import com.altsdrop.feature.login.domain.repository.LoginRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val resources: Resources
) {
    suspend operator fun invoke(context: Context): SignInState {
        return loginRepository.googleSignIn(context).fold(
            onSuccess = {
                SignInState.Success
            },
            onFailure = {
                FirebaseCrashlytics.getInstance().log(
                    "GoogleSignInUseCase.invoke: $it"
                )
                SignInState.Error(resources.getString(R.string.sign_in_error))
            }
        )
    }
}