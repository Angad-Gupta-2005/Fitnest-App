package com.angad.fitnestx.signup_and_login

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.angad.fitnestx.R
import com.angad.fitnestx.databinding.FragmentRegisterBinding
import com.angad.fitnestx.models.User_detail
import com.angad.fitnestx.objects.Utils
import com.angad.fitnestx.repository.UserRepository
import com.angad.fitnestx.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

class RegisterFragment : Fragment(), View.OnClickListener, View.OnFocusChangeListener {

//    Creating an instance of binding
    private lateinit var binding: FragmentRegisterBinding

//    Initialised the viewModel
    private val viewModel = AuthViewModel(UserRepository())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

    //    Initialised the binding
        binding = FragmentRegisterBinding.inflate(layoutInflater)

        binding.etFirstName.onFocusChangeListener = this
        binding.etLastName.onFocusChangeListener = this
        binding.etEmail.onFocusChangeListener = this
        binding.etPassword.onFocusChangeListener = this

    //    On click register button, create a new user
        onClickRegisterButton()

    //    On click login text go to the loginScreen
        onClickLoginText()

        return binding.root
    }

    private fun onClickLoginText() {
        binding.loginTxt.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun onClickRegisterButton() {
        binding.registerBtn.setOnClickListener {
        //    Show the loader
            Utils.showDialog(requireContext(), "Signing You...")

        //    Taking input data from input field
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Utils.apply {
                    hideDialog()
                    Toast.makeText(context, "Please fill all details...", Toast.LENGTH_SHORT).show()
                }
            } else{
            //    Creating an instance of user class which available in models package
                val user = User_detail(firstName = firstName, lastName = lastName, email = email, password = password)
                viewModel.createUserWithEmailAndPassword(user)

                lifecycleScope.launch {
                    viewModel.isUserRegisterSuccessfully.collect{
                        if (it){
                        //    Hide the dialog
                            Utils.hideDialog()
                        //    Go to the ProfileInputFragment
                            findNavController().navigate(R.id.action_registerFragment_to_profileInputFragment)
                        } else{
                        //    Handling the error
                            Utils.hideDialog()

                        }
                    }
                }
            }



        }
    }

//    Validate the different field
//    1. Function to validate the first name field
    private fun validateFirstName(): Boolean {
        var errorMessage: String? = null
        val value: String = binding.etFirstName.text.toString()
        if (value.isEmpty()) {
            errorMessage = "First name is required"
        }

        if (errorMessage != null) {
            binding.etFirstNameLayout.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

//    2. Function to validate the last name field
    private fun validateLastName(): Boolean {
        var errorMessage: String? = null
        val value: String = binding.etLastName.text.toString()
        if (value.isEmpty()) {
            errorMessage = "This field is required"
        }

        if (errorMessage != null) {
            binding.etLastNameLayout.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    //  2.  Function to validate the email field
    private fun validateEmail(): Boolean {
        var errorMessage: String? = null
        val value: String = binding.etEmail.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Email is required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            errorMessage = "Invalid email address"
        }

        if (errorMessage != null) {
            binding.etEmailLayout.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }


    //  3.  Function to validate the password field
    private fun validatePassword(): Boolean {
        var errorMessage: String? = null
        val value: String = binding.etPassword.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Password is required"
        } else if (value.length < 6) {
            errorMessage = "Password must be 6 characters long"
        } else if (value.length > 10) {
            errorMessage = "Password must be less than 10 characters long"
        }

        if (errorMessage != null) {
            binding.etPasswordLayout.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }


//    On click listener
    override fun onClick(v: View?) {}


//    On user switch the input field validate the previous field
    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
//                validating the first name field
                R.id.et_first_name -> {
                    if (hasFocus) {
                        if (binding.etFirstNameLayout.isErrorEnabled) {
                            binding.etFirstNameLayout.isErrorEnabled = false
                        }
                    } else {
                        validateFirstName()
                    }
                }

//                Validating the last name field see in future
                R.id.et_last_name -> {
                    if (hasFocus) {
                        if (binding.etLastNameLayout.isErrorEnabled) {
                            binding.etLastNameLayout.isErrorEnabled = false
                        }
                    } else {
                        validateLastName()
                    }
                }

//                validating the email field
                R.id.et_email -> {
                    if (hasFocus) {
                        if (binding.etEmailLayout.isErrorEnabled) {
                            binding.etEmailLayout.isErrorEnabled = false
                        }
                    } else {
                        validateEmail()
                    }
                }

//                validating the password field
                R.id.et_password -> {
                    if (hasFocus) {
                        if (binding.etPasswordLayout.isErrorEnabled) {
                            binding.etPasswordLayout.isErrorEnabled = false
                        }
                    } else {
                        validatePassword()
                    }
                }
            }
        }
    }


}