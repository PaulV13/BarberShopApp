package com.example.barbershopapp.data.response

import com.example.barbershopapp.data.usecase.Register

sealed class RegisterResult {
    data object Error: RegisterResult()
    data class Success(val verified: Boolean) : RegisterResult()
}
