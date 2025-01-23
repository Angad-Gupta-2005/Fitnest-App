package com.angad.fitnestx.dashboard

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.angad.fitnestx.databinding.FragmentHomeBinding
import com.angad.fitnestx.models.BMIResult
import com.angad.fitnestx.repository.UserRepository
import com.angad.fitnestx.viewmodels.AuthViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


class HomeFragment : Fragment() {

//    Creating an instance of binding
    private lateinit var binding: FragmentHomeBinding

//    Initialised the viewModels
    private val viewModel = AuthViewModel(UserRepository())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

    //    Initialised the binding
        binding = FragmentHomeBinding.inflate(layoutInflater)

        calculateBMI()

        displayUserName()


        return binding.root
    }

    private fun displayUserName() {
        viewModel.loadUserName()

        viewModel.userName.observe(viewLifecycleOwner){ name ->
            binding.userName.text = name
        }
    }


    private fun calculateBMI() {


        viewModel.fetchUserWeightAndHeight()


        viewModel.userWeightAndHeight.observe(viewLifecycleOwner){ result ->
            val weight = result.first ?: 0f
            val height = result.second ?: 0f

            if (weight > 0f && height > 0f){
                viewModel.calculateUserBMI(weight, height)
            }
        }


        viewModel.bmiResult.observe(viewLifecycleOwner){ bmiResult ->
            bmiResult?.let {
                setupPieChart(it)
            }
        }
    }

    private fun setupPieChart(bmiResult: BMIResult) {
        val pieEntries = ArrayList<PieEntry>()

    // Add the user's BMI value
        pieEntries.add(PieEntry(bmiResult.value, bmiResult.category))

    // Optional: Add a reference range to provide context
        when (bmiResult.category) {
            "Underweight" -> {
                pieEntries.add(PieEntry(18.5f - bmiResult.value, "To Normal"))
            }

            "Normal" -> {
                pieEntries.add(PieEntry(24.9f - bmiResult.value, "Remaining Normal Range"))
            }

            "Overweight" -> {
                pieEntries.add(PieEntry(29.9f - bmiResult.value, "To Obese"))
            }

            "Obese" -> {
                // Optional: No additional category as the user is already obese
            }
        }

    // Configure the DataSet for the PieChart
        val dataSet = PieDataSet(pieEntries, "BMI Categories")

    // Set colors dynamically based on the BMI category
        val chartColors = when (bmiResult.category) {
            "Underweight" -> listOf(Color.CYAN, Color.LTGRAY) // CYAN for Underweight
            "Normal" -> listOf(Color.GREEN, Color.LTGRAY)     // GREEN for Normal
            "Overweight" -> listOf(Color.YELLOW, Color.LTGRAY) // YELLOW for Overweight
            "Obese" -> listOf(Color.RED, Color.LTGRAY)        // RED for Obese
            else -> listOf(Color.GRAY, Color.LTGRAY)          // Default fallback
        }
        dataSet.colors = chartColors
        dataSet.valueTextSize = 16f

        val pieData = PieData(dataSet)

    // Configure the PieChart
        binding.bmiChart.apply {
            data = pieData
            description.isEnabled = false
            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)
            setTransparentCircleColor(Color.LTGRAY)
            centerText = "BMI Chart"
            animateY(1000)
            invalidate() // Refresh the chart
        }

    }

}