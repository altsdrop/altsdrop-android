package com.altsdrop.feature.settings.domain.usecase

import com.altsdrop.feature.settings.domain.model.User
import com.altsdrop.feature.settings.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User = userRepository.getUser()
}