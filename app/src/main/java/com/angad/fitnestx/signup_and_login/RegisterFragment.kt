package com.angad.fitnestx.signup_and_login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.angad.fitnestx.R
import com.angad.fitnestx.databinding.FragmentRegisterBinding
import com.angad.fitnestx.models.User_detail
import com.angad.fitnestx.objects.Utils
import com.angad.fitnestx.viewmodels.AuthViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

//    Creating an instance of binding
    private lateinit var binding: FragmentRegisterBinding

//    Initialised the viewModel
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

    //    Initialised the binding
        binding = FragmentRegisterBinding.inflate(layoutInflater)

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
                        }
                    }
                }
            }



        }
    }

}