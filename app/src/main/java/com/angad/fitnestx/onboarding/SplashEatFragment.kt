package com.angad.fitnestx.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.angad.fitnestx.R
import com.angad.fitnestx.databinding.FragmentSplashEatBinding

class SplashEatFragment : Fragment() {

    private lateinit var binding: FragmentSplashEatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashEatBinding.inflate(layoutInflater)

        onNextButtonClick()
        return binding.root
    }

    private fun onNextButtonClick() {
        binding.btnSplash3.setOnClickListener {
            findNavController().navigate(R.id.action_splashEatFragment_to_splashSleepFragment)
        }
    }

}