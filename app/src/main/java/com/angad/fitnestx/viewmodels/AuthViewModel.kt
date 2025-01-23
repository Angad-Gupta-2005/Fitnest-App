package com.angad.fitnestx.viewmodels

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angad.fitnestx.models.BMIResult
import com.angad.fitnestx.models.PersonalDetails
import com.angad.fitnestx.models.User_detail
import com.angad.fitnestx.objects.Utils
import com.angad.fitnestx.repository.UserRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository): ViewModel() {

    //    Stateflow for user name
   private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _userWeightAndHeight = MutableLiveData<Pair<Float?, Float?>>()
    val userWeightAndHeight: LiveData<Pair<Float?, Float?>> = _userWeightAndHeight

    // Function to calculate BMI using height and weight
    private val _bmiResult = MutableLiveData<BMIResult>()
    val bmiResult: LiveData<BMIResult> get() = _bmiResult

    //    functionality to check user is register or not
    private val _isUserRegisterSuccessfully = MutableStateFlow(false)
    val isUserRegisterSuccessfully  = _isUserRegisterSuccessfully

    //    functionality after the user signIn successfully
    private val _isSignedInSuccessfully = MutableStateFlow(false)
    val isSignedInSuccessfully = _isSignedInSuccessfully


    //    Creating a state flow for checking that our data is saved or not
    private val _isDataSaved = MutableStateFlow(false)
    var isDataSaved: StateFlow<Boolean> = _isDataSaved

    //    For getting the current user
    private val _isCurrentUser = MutableStateFlow(false)
    val isCurrentUser = _isCurrentUser

    init {
        Utils.getAuthInstance()?.let {
            _isCurrentUser.value = true
        }
    }

//    Function that create user using email and password
    fun createUserWithEmailAndPassword(user: User_detail) {
        Utils.getAuthInstance().createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                user.uid = Utils.getCurrentUserId()
                if (task.isSuccessful){
                    FirebaseDatabase.getInstance("https://blinkit-clone-f610a-default-rtdb.asia-southeast1.firebasedatabase.app")
                        .getReference("FitnestUser").child("UserInfo").child(user.uid!!).setValue(user)
                        .addOnSuccessListener {
                            Log.d("Success", "signInWithPhoneAuthCredential: ")
                        }
                        .addOnFailureListener { e ->
                            Log.e("Unsuccessful", "signInWithPhoneAuthCredential: ${e.message}" )
                        }
                    // Sign in success, update UI with the signed-in user's information
                    _isUserRegisterSuccessfully.value = true
                } else{
                    // Registration failed, display a message and update the UI
                }
            }
    }

//    Function that signIn user with email and password
    fun signInUserWithEmailAndPassword(email: String, password: String){
        Utils.getAuthInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                //    Updating the state
                    _isSignedInSuccessfully.value = true
                } else{
                    //  Sign in failed, display a message and update the UI
                }
            }
    }

    fun saveData(personalDetails: PersonalDetails){

    //    Getting the current user
        personalDetails.uid = Utils.getCurrentUserId()
    //    Creating a node for adding all product
        FirebaseDatabase.getInstance("https://blinkit-clone-f610a-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("FitnestUser").child("UserPersonal").child(personalDetails.uid!!).setValue(personalDetails)
            .addOnSuccessListener {
                _isDataSaved.value = true
            }
    }

    //    Getting the currentUser uid
    private val userId = Utils.getAuthInstance().currentUser?.uid.toString()

//    Function that load the user name
    fun loadUserName(){
        repository.fetchUserName(userId).observeForever { name ->
            _userName.value = name
        }
    }

    fun fetchUserWeightAndHeight() {
        repository.fetchUserWeightAndHeight(userId).observeForever { result ->
            _userWeightAndHeight.value = result
        }
    }

//    Function that calculate user bmi
    fun calculateUserBMI(weight: Float, height: Float){
        val heightM =  height /100

        if (height > 0.00f){
            val res = weight / (heightM * heightM)

            val category = when {
                res < 18.5 -> "Underweight"
                res in 18.5..24.9 -> "Normal"
                res in 25.0..29.9 -> "Overweight"
                else -> "Obese"
            }

            val bmi = BMIResult(value = res, category = category)
            _bmiResult.value = bmi

        } else{
            _bmiResult.value = BMIResult( value = 0f, category = "Invalid")

        }
    }


}