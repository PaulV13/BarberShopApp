package com.example.barbershopapp.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.barbershopapp.databinding.FragmentRegisterBinding
import com.example.barbershopapp.domain.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment: Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var auth: FirebaseAuth

    private val registerViewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validateName()
        validateEmail()
        validatePhone()
        validatePassword()

        registerViewModel.userEmail.observe(viewLifecycleOwner) {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
        }

        registerViewModel.errorMessage.observe(viewLifecycleOwner){
            Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
        }

        binding.btnSignUp.setOnClickListener {
            val user = User(
                name = binding.etName.text.toString(),
                email = binding.etEmail.text.toString(),
                phone = binding.etPhone.text.toString(),
                password = binding.etPassword.text.toString()
            )

            registerViewModel.register(user)
        }

        binding.tvLogIn.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
    }

    private fun validateName() {
        binding.etName.doAfterTextChanged {
            if(registerViewModel.validateName(it)){
                binding.textInputName.isErrorEnabled = true
                binding.textInputName.error = "The name mustn't be empty."
            }else{
                binding.textInputName.isErrorEnabled = false
            }
        }
    }

    private fun validateEmail() {
        binding.etEmail.doAfterTextChanged {
            if(!registerViewModel.validateEmail(it)){
                binding.textInputEmail.isErrorEnabled = true
                binding.textInputEmail.error = "The email is not valid."
            }else{
                binding.textInputEmail.isErrorEnabled = false
            }
        }
    }

    private fun validatePhone() {
        binding.etPhone.doAfterTextChanged {
            if(!registerViewModel.validatePhone(it)){
                binding.textInputPhone.isErrorEnabled = true
                binding.textInputPhone.error = "The phone must be nine digits."
            }else{
                binding.textInputPhone.isErrorEnabled = false
            }
        }
    }

    private fun validatePassword() {
        binding.etPassword.doAfterTextChanged {
            if(!registerViewModel.validatePassword(it)){
                binding.textInputPassword.isErrorEnabled = true
                binding.textInputPassword.error = "The password must be eight characters or longer."
            }else{
                binding.textInputPassword.isErrorEnabled = false
            }
        }
    }
}