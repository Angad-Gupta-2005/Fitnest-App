package com.angad.fitnestx.signup_and_login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.angad.fitnestx.R
import com.angad.fitnestx.databinding.FragmentLoginBinding
import com.angad.fitnestx.objects.Utils
import com.angad.fitnestx.repository.UserRepository
import com.angad.fitnestx.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

//    Creating an instance of binding
    private lateinit var binding: FragmentLoginBinding

//    Initialised the viewModel
    private val viewModel = AuthViewModel(UserRepository())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

    //    Initialised the binding
        binding = FragmentLoginBinding.inflate(layoutInflater)

    //    Calling the function that perform the functionality to login the user
        onClickLoginButton()

    //    Calling the function that perform the functionality to goto the register screen on click register text
        onClickRegisterText()

        return binding.root
    }

    private fun onClickRegisterText() {
        binding.registerTxt.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun onClickLoginButton() {
        binding.loginBtn.setOnClickListener {

            Utils.showDialog(requireContext(), "Logging..")

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Utils.apply {
                    hideDialog()
                    Toast.makeText(context, "Please fill all details", Toast.LENGTH_SHORT).show()
                }
            } else{

            //    Login the user
                viewModel.signInUserWithEmailAndPassword(email, password)

                lifecycleScope.launch {
                    viewModel.isSignedInSuccessfully.collect{
                        if (it){
                            Utils.hideDialog()
                            findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)
                        }
                        else{
                            Utils.hideDialog()
                        }
                    }
                }

            }
        }
    }

}