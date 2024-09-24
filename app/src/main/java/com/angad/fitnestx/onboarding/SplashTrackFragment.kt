package com.angad.fitnestx.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.angad.fitnestx.R
import com.angad.fitnestx.databinding.FragmentSplashTrackBinding

class SplashTrackFragment : Fragment() {

    private lateinit var binding: FragmentSplashTrackBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashTrackBinding.inflate(layoutInflater)

        onNextButtonClick()
        return binding.root
    }

    private fun onNextButtonClick() {
        binding.btnSplash1.setOnClickListener {
            findNavController().navigate(R.id.action_splashTrackFragment_to_splashBurnFragment4)
        }
    }

}