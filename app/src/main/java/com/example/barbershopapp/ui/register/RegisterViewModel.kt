package com.example.barbershopapp.ui.register

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barbershopapp.data.response.LoginResult
import com.example.barbershopapp.data.usecase.Register
import com.example.barbershopapp.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private var registerUseCase: Register
): ViewModel() {

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String>
        get() = _userEmail

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun register(user: User) {
        viewModelScope.launch {
            when (registerUseCase(user)) {
                LoginResult.Error -> _errorMessage.value = "Credential invalid!!!"
                is LoginResult.Success -> _userEmail.value = user.email
            }
        }
    }

    fun validateEmail(email: Editable?): Boolean {
        return email!!.matches("[^@]+@[^@]+.[a-zA-Z]{2,}\$".toRegex())
    }

    fun validateName(name: Editable?): Boolean {
        return name!!.isEmpty()
    }

    fun validatePhone(phone: Editable?): Boolean {
        return phone!!.length == 9
    }

    fun validatePassword(password: Editable?): Boolean {
        return password!!.length >= 8
    }
}