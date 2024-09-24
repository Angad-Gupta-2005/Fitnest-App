package com.angad.fitnestx.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.angad.fitnestx.R
import com.angad.fitnestx.databinding.FragmentSplash2Binding


class Splash2Fragment : Fragment() {

    private lateinit var binding: FragmentSplash2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplash2Binding.inflate(layoutInflater)

        onGetStartedButtonClick()
        return binding.root
    }

    private fun onGetStartedButtonClick() {
        binding.btnGetStarted.setOnClickListener {
            findNavController().navigate(R.id.action_splash2Fragment_to_splashTrackFragment)
        }
    }

}