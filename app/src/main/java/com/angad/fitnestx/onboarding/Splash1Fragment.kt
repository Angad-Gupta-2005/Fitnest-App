package com.angad.fitnestx.onboarding

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.angad.fitnestx.R
import com.angad.fitnestx.activity.DashboardActivity
import com.angad.fitnestx.databinding.FragmentSplash1Binding
import com.angad.fitnestx.objects.Utils
import com.angad.fitnestx.repository.UserRepository
import com.angad.fitnestx.viewmodels.AuthViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class Splash1Fragment : Fragment() {

private lateinit var binding: FragmentSplash1Binding

//    Initialised the viewModel
    private val viewModel = AuthViewModel(UserRepository())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplash1Binding.inflate(layoutInflater)

        //        Use of Handler for splash screen
        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                viewModel.isCurrentUser.collect{
                    if (it){
                        startActivity(Intent(requireActivity(), DashboardActivity::class.java))
                        requireActivity().finish()
                    } else{
                        findNavController().navigate(R.id.action_splash1Fragment_to_splash2Fragment)
                    }
                }
            }
        }, 2000)
        return  binding.root
    }

}