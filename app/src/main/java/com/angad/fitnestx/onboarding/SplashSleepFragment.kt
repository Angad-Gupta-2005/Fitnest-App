package com.angad.fitnestx.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.angad.fitnestx.R
import com.angad.fitnestx.activity.AuthActivity
import com.angad.fitnestx.databinding.FragmentSplashSleepBinding

class SplashSleepFragment : Fragment() {

    private lateinit var binding: FragmentSplashSleepBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashSleepBinding.inflate(layoutInflater)

    //    On click next button start the new activity
        onClickNextButton()

        return binding.root
    }

    private fun onClickNextButton() {
        binding.btnSplash4.setOnClickListener {
            startActivity(Intent(requireActivity(), AuthActivity::class.java))
            requireActivity().finish()
        }
    }

}