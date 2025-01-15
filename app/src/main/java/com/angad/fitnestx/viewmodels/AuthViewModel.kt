package com.angad.fitnestx.viewmodels

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angad.fitnestx.models.PersonalDetails
import com.angad.fitnestx.models.User_detail
import com.angad.fitnestx.objects.Utils
import com.angad.fitnestx.repository.UserRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel(private val repository: UserRepository): ViewModel() {

    //    Stateflow for user name
   private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName


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
        Utils.getAuthInstance().let {
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

//    Function that load the user name
    fun loadUserName(){
    //    Getting the currentUser uid
        val userId = Utils.getAuthInstance().currentUser?.uid.toString()
        repository.fetchUserName(userId).observeForever { name ->
            _userName.value = name
        }
    }

}