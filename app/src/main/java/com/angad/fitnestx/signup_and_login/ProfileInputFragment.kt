package com.angad.fitnestx.signup_and_login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.angad.fitnestx.R
import com.angad.fitnestx.databinding.FragmentProfileInputBinding
import com.angad.fitnestx.models.PersonalDetails
import com.angad.fitnestx.objects.Constants
import com.angad.fitnestx.objects.Utils
import com.angad.fitnestx.repository.UserRepository
import com.angad.fitnestx.viewmodels.AuthViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class ProfileInputFragment : Fragment(),View.OnClickListener, View.OnFocusChangeListener {

//    Creating an instance of binding
    private lateinit var binding: FragmentProfileInputBinding

//    Initialised the viewModel
    private val viewModel = AuthViewModel(UserRepository())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

    //    Initialised the binding
        binding = FragmentProfileInputBinding.inflate(layoutInflater)

        binding.etChooseGender.onFocusChangeListener = this
        binding.etDateOfBirth.onFocusChangeListener = this
        binding.etWeight.onFocusChangeListener = this
        binding.etHeight.onFocusChangeListener = this

    //    Calling the function that set auto complete textview
        setAutoCompleteTextViews()

    //    Calling the function that perform functionality on click next button
        onClickNextButton()

        return binding.root
    }


    private fun setAutoCompleteTextViews() {
        val gender = ArrayAdapter(requireContext(), R.layout.show_list, Constants.genderList)
        binding.etChooseGender.setAdapter(gender)
    }

    private fun onClickNextButton() {
        binding.nextBtn.setOnClickListener {
        //    Show the loader
            Utils.showDialog(requireContext(),"Uploading data")

            val gender = binding.etChooseGender.text.toString()
            val dob = binding.etDateOfBirth.text.toString()
            val weight = binding.etWeight.text.toString()
            val height = binding.etHeight.text.toString()

            if (gender.isEmpty() || dob.isEmpty() || weight.isEmpty() || height.isEmpty()){
                Toast.makeText(context, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else{

                val personalDetail = PersonalDetails(
                    gender = gender,
                    dob = dob,
                    weight = weight,
                    height = height.toInt()
                )

            //    Save the data into the firebase database
                savePersonalData(personalDetail)
            }
        }
    }

//    Function that save the user personal data into the firebase database
    private fun savePersonalData(personalDetail: PersonalDetails) {
        viewModel.saveData(personalDetail)
        lifecycleScope.launch {
            viewModel.isDataSaved.collect{
                if (it){
                    Utils.hideDialog()
                    Toast.makeText(context, "Your data is saved", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_profileInputFragment_to_improveShapeFragment)
                }
                else{
                    Utils.hideDialog()
                    Toast.makeText(context, "Somethings went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


//    1. Function to validate the gender field
    private fun validateGender(): Boolean {
        var errorMessage: String? = null
        val value: String = binding.etChooseGender.text.toString().trim()

        if (value.isEmpty()) {
            errorMessage = "Gender is required"
        } else {
            val validGenders = listOf("Male", "Female", "Other")
            if (!validGenders.contains(value)) {
                errorMessage = "Invalid gender. Please select Male, Female, or Other."
            }
        }

        if (errorMessage != null) {
            binding.etChooseGenderLayout.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        } else {
            binding.etChooseGenderLayout.isErrorEnabled = false
        }

        return errorMessage == null
    }


    //    2. Function to validate the date of birth
    private fun validateDate(): Boolean {
        var errorMessage: String? = null
        val value: String = binding.etDateOfBirth.text.toString()
        val dateFormat = "dd/MM/yyyy" // Expected date format yyyy-MM-dd

        if (value.isEmpty()) {
            errorMessage = "Date is required"
        } else {
            try {
                val formatter = DateTimeFormatter.ofPattern(dateFormat)
                LocalDate.parse(value, formatter) // Validates the date format and logical correctness
            } catch (e: DateTimeParseException) {
                errorMessage = "Invalid date format. Please use $dateFormat"
            }
        }

        if (errorMessage != null) {
            binding.etDOBLayout.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        } else {
            binding.etDOBLayout.isErrorEnabled = false
        }

        return errorMessage == null
    }

//    3. Function to validate weight
    private fun validateWeight(): Boolean {
        var errorMessage: String? = null
        val value: String = binding.etWeight.text.toString()

        if (value.isEmpty()) {
            errorMessage = "Weight is required"
        } else {
            val weight = value.toFloatOrNull()
            if (weight == null || weight <= 0) {
                errorMessage = "Invalid weight. Please enter a positive number."
            }
        }

        if (errorMessage != null) {
            binding.etWeightLayout.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        } else {
            binding.etWeightLayout.isErrorEnabled = false
        }

        return errorMessage == null
    }

//    4. Function to validate the height
    private fun validateHeight(): Boolean {
        var errorMessage: String? = null
        val value: String = binding.etHeight.text.toString()

        if (value.isEmpty()) {
            errorMessage = "Height is required"
        } else {
            val height = value.toFloatOrNull()
            if (height == null || height <= 0 || height > 300) {
                errorMessage = "Invalid height. Please enter a value between 1 and 300 cm."
            }
        }

        if (errorMessage != null) {
            binding.etHeightLayout.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        } else {
            binding.etHeightLayout.isErrorEnabled = false
        }

        return errorMessage == null
    }


    //    On user switch the input field validate the previous field
    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
//               1. validating the gender field
                R.id.etChooseGender -> {
                    if (hasFocus) {
                        if (binding.etChooseGenderLayout.isErrorEnabled) {
                            binding.etChooseGenderLayout.isErrorEnabled = false
                        }
                    } else {
                        validateGender()
                    }
                }

//               2. Validating the date of birth field see in future
                R.id.et_dateOfBirth -> {
                    if (hasFocus) {
                        if (binding.etDOBLayout.isErrorEnabled) {
                            binding.etDOBLayout.isErrorEnabled = false
                        }
                    } else {
                        validateDate()
                    }
                }

//               3. validating the weight field
                R.id.et_weight -> {
                    if (hasFocus) {
                        if (binding.etWeightLayout.isErrorEnabled) {
                            binding.etWeightLayout.isErrorEnabled = false
                        }
                    } else {
                        validateWeight()
                    }
                }

//               4. validating the height field
                R.id.et_height -> {
                    if (hasFocus) {
                        if (binding.etHeightLayout.isErrorEnabled) {
                            binding.etHeightLayout.isErrorEnabled = false
                        }
                    } else {
                        validateHeight()
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) { }


}