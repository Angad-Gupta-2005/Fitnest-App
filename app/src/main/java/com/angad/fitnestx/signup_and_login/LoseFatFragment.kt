package com.angad.fitnestx.signup_and_login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.angad.fitnestx.R
import com.angad.fitnestx.databinding.FragmentLoseFatBinding

class LoseFatFragment : Fragment() {

    private lateinit var binding: FragmentLoseFatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

    //    Initialised the binding
        binding = FragmentLoseFatBinding.inflate(layoutInflater)

        onClickNextButton()

        return binding.root
    }

    private fun onClickNextButton() {
        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loseFatFragment_to_loginFragment)
        }
    }

}