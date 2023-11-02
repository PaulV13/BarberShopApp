package com.example.barbershopapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barbershopapp.data.response.LoginResult
import com.example.barbershopapp.data.usecase.Login
import com.example.barbershopapp.data.usecase.LoginGoogle
import com.example.barbershopapp.data.usecase.Register
import com.example.barbershopapp.domain.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private var loginUseCase: Login,
    private var loginGoogleUseCase: LoginGoogle,
    private val db: FirebaseFirestore
) : ViewModel() {

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun login(email: String, password: String) {
        viewModelScope.launch {
            when (loginUseCase(email, password)) {
                LoginResult.Error -> _errorMessage.value = "Email or Password incorrect!!!"
                is LoginResult.Success -> _userEmail.value = email
            }
        }
    }

    fun loginGoogle(credential: AuthCredential) {
        viewModelScope.launch {
            val firebaseUser = loginGoogleUseCase(credential)

            if(firebaseUser != null){
                val user = User(
                    name = firebaseUser.displayName.toString(),
                    email = firebaseUser.email.toString(),
                    phone = firebaseUser.phoneNumber.toString(),
                    password = ""
                )

                val newUser = hashMapOf(
                    "name" to user.name,
                    "phone" to user.phone,
                )

                db.collection("users")
                    .document(user.email)
                    .set(newUser)

                _userEmail.value = firebaseUser.email.toString()
            }else{
                _errorMessage.value = "Google login failed."
            }
        }
    }
}