package com.angad.fitnestx.onboarding

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.angad.fitnestx.R
import com.angad.fitnestx.databinding.FragmentSplash1Binding


class Splash1Fragment : Fragment() {

private lateinit var binding: FragmentSplash1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplash1Binding.inflate(layoutInflater)

        //        Use of Handler for splash screen
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splash1Fragment_to_splash2Fragment)
        }, 3000)
        return  binding.root
    }

}