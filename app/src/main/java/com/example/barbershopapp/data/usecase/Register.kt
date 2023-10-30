package com.example.barbershopapp.data.usecase

import com.example.barbershopapp.data.network.RegisterService
import com.example.barbershopapp.data.response.LoginResult
import com.example.barbershopapp.data.response.RegisterResult
import com.example.barbershopapp.domain.User
import javax.inject.Inject

class Register @Inject constructor(
    private val registerService: RegisterService
) {
    suspend operator fun invoke(user: User): RegisterResult = registerService.registerUser(user)
}