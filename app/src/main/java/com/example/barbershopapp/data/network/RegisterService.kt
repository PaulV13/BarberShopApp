package com.example.barbershopapp.data.network

import com.example.barbershopapp.data.response.RegisterResult
import com.example.barbershopapp.domain.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) {
    suspend fun registerUser(user: User): RegisterResult {
        val result = try {
           firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).await()
        }catch (e: Exception){
            return RegisterResult.Error
        }

        val newUser = hashMapOf(
            "name" to user.name,
            "phone" to user.phone,
        )

        db.collection("users")
            .document(user.email)
            .set(newUser)

        return RegisterResult.Success(result?.user?.isEmailVerified ?: false)
    }
}