package com.example.barbershopapp.data.response

sealed class RegisterResult {
    data object Error: RegisterResult()
    data class Success(val verified: Boolean) : RegisterResult()
}
