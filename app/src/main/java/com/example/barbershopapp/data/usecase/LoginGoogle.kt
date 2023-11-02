package com.example.barbershopapp.data.usecase

import com.example.barbershopapp.data.network.AuthService
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class LoginGoogle @Inject constructor(
    private val authService: AuthService
) {
    suspend operator fun invoke(credential: AuthCredential): FirebaseUser? = authService.authGoogle(credential)
}