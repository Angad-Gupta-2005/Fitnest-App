package com.angad.fitnestx.signup_and_login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.angad.fitnestx.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

//    Creating an instance of binding
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

    //    Initialised the binding
        binding = FragmentLoginBinding.inflate(layoutInflater)




        return binding.root
    }

}