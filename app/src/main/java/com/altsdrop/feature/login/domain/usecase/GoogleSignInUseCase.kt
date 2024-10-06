package com.altsdrop.feature.login.domain.usecase

import com.altsdrop.feature.login.domain.model.SignInState
import com.altsdrop.feature.login.domain.repository.LoginRepository
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {
    suspend operator fun invoke(): SignInState {
        return loginRepository.googleSignIn().fold(
            onSuccess = {
                SignInState.Success
            },
            onFailure = {
                SignInState.Error
            }
        )
    }
}