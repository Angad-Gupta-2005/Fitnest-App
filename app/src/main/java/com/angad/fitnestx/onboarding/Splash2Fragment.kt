package com.angad.fitnestx.onboarding

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

//    setting the status bar color
    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the status bar color
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.anakiwa)
    }

    private fun onGetStartedButtonClick() {
        binding.btnGetStarted.setOnClickListener {
            findNavController().navigate(R.id.action_splash2Fragment_to_splashTrackFragment)
        }
    }

}