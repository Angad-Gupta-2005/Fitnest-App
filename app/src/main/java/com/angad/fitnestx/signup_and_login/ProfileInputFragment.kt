package com.angad.fitnestx.signup_and_login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.angad.fitnestx.R
import com.angad.fitnestx.databinding.FragmentProfileInputBinding
import com.angad.fitnestx.models.PersonalDetails
import com.angad.fitnestx.objects.Constants
import com.angad.fitnestx.objects.Utils
import com.angad.fitnestx.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

class ProfileInputFragment : Fragment() {

//    Creating an instance of binding
    private lateinit var binding: FragmentProfileInputBinding

//    Initialised the viewModel
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

    //    Initialised the binding
        binding = FragmentProfileInputBinding.inflate(layoutInflater)

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
            }
        }
    }

}