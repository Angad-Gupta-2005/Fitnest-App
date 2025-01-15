package com.angad.fitnestx.signup_and_login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.angad.fitnestx.activity.DashboardActivity
import com.angad.fitnestx.databinding.FragmentWelcomeBinding
import com.angad.fitnestx.repository.UserRepository
import com.angad.fitnestx.viewmodels.AuthViewModel

class WelcomeFragment : Fragment() {

//    Creating an instance of binding
    private lateinit var binding: FragmentWelcomeBinding

//    Initialised the viewModel
    private val viewModel = AuthViewModel(UserRepository())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

    //    Initialised the binding
        binding = FragmentWelcomeBinding.inflate(layoutInflater)

    //    Display the user name that fetch from firebase database
        displayUserName()

    //    On click go to home button start new activity
        onClickGoToHomeButton()

        return binding.root
    }

    private fun onClickGoToHomeButton() {
        binding.goToHomeBtn.setOnClickListener {
            startActivity(Intent(requireActivity(), DashboardActivity::class.java))
            requireActivity().finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayUserName() {

        viewModel.loadUserName()

        viewModel.userName.observe(viewLifecycleOwner){ name ->
            binding.userName.text = "Welcome $name"
        }

    }

}