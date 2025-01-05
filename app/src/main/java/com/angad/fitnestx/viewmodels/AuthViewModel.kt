package com.angad.fitnestx.viewmodels

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.angad.fitnestx.models.User_detail
import com.angad.fitnestx.objects.Utils
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow

class AuthViewModel: ViewModel() {


    //    functionality after the user signIn successfully
    private val _isSignedInSuccessfully = MutableStateFlow(false)
    val isSignedInSuccessfully = _isSignedInSuccessfully

//    Function that create user using email and password
    fun singInWithEmailAndPassword(user: User_detail) {
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
                    _isSignedInSuccessfully.value = true
                } else{
                    // Sign in failed, display a message and update the UI
                }
            }
    }
}