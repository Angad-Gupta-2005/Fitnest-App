package com.angad.fitnestx.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.angad.fitnestx.R
import com.angad.fitnestx.dashboard.CameraFragment
import com.angad.fitnestx.dashboard.HomeFragment
import com.angad.fitnestx.dashboard.ProfileFragment
import com.angad.fitnestx.dashboard.WorkoutFragment
import com.angad.fitnestx.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

//    Creating an instance of binding
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

    //    Initialised the binding
        binding = ActivityDashboardBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Set default fragment
        replaceFragment(HomeFragment())

        // Handle navigation item clicks
        val bottomNavigationView = binding.bottomMenu
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> replaceFragment(HomeFragment())
                R.id.workoutFragment  -> replaceFragment(WorkoutFragment())
                R.id.cameraFragment  -> replaceFragment(CameraFragment())
                R.id.profileFragment  -> replaceFragment(ProfileFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerDashboard, fragment)
            .commit()
    }


}