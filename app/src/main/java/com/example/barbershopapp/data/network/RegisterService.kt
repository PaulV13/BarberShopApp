package com.example.barbershopapp.data.network

import com.example.barbershopapp.data.response.LoginResult
import com.example.barbershopapp.domain.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) {
    suspend fun registerUser(user: User): LoginResult {
        val result = try {
           firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).await()
        }catch (e: Exception){
            return LoginResult.Error
        }

        val newUser = hashMapOf(
            "name" to user.name,
            "phone" to user.phone,
        )

        db.collection("users")
            .document(user.email)
            .set(newUser)

        return LoginResult.Success(result?.user?.isEmailVerified ?: false)
    }
}