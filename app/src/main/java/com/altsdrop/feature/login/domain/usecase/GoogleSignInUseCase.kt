package com.altsdrop.feature.login.domain.usecase

import com.altsdrop.feature.login.domain.model.SignInState
import com.altsdrop.feature.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {

    operator fun invoke(): Flow<SignInState> {
        return flow {
            emit(SignInState.Loading)
            loginRepository.googleSignIn().fold(
                onSuccess = {
                    emit(SignInState.Success)
                },
                onFailure = {
                    emit(SignInState.Error)
                }
            )
        }
    }
}