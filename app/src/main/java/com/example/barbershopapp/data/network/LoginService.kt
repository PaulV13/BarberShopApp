package com.example.barbershopapp.data.network

import com.example.barbershopapp.data.response.LoginResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginService @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun loginUser(email: String, password: String): LoginResult {
        val result = try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
        }catch (e: Exception){
            return LoginResult.Error
        }
        return LoginResult.Success(result?.user?.isEmailVerified ?: false)
    }
}
