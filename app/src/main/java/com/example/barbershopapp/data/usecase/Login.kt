package com.example.barbershopapp.data.usecase

import com.example.barbershopapp.data.network.LoginService
import com.example.barbershopapp.data.response.LoginResult
import javax.inject.Inject

class Login @Inject constructor(
    private val loginService: LoginService
) {
    suspend operator fun invoke(email: String, password: String): LoginResult = loginService.loginUser(email,password)
}