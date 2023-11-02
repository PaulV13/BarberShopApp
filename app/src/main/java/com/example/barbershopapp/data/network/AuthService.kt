package com.example.barbershopapp.data.network

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun authGoogle(credential: AuthCredential): FirebaseUser? {

        val result = firebaseAuth.signInWithCredential(credential).await()

        return try {
            result.user
        }catch (e: Exception){
            null
        }
    }
}