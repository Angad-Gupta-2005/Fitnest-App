package com.angad.fitnestx.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.angad.fitnestx.R
import com.angad.fitnestx.databinding.FragmentSplashBurnBinding

class SplashBurnFragment : Fragment() {

    private lateinit var binding: FragmentSplashBurnBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashBurnBinding.inflate(layoutInflater)

        onNextButtonClicked()
        return binding.root
    }

    private fun onNextButtonClicked() {
        binding.btnSplash2.setOnClickListener {
            findNavController().navigate(R.id.action_splashBurnFragment4_to_splashEatFragment)
        }
    }

}