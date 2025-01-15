package com.angad.fitnestx.repository

import androidx.lifecycle.MutableLiveData
import com.angad.fitnestx.models.User_detail
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserRepository {

    val db = FirebaseDatabase.getInstance("https://blinkit-clone-f610a-default-rtdb.asia-southeast1.firebasedatabase.app")
        .getReference("FitnestUser")

    //    Function that fetch the user name from the firebase and show into the screen
    fun fetchUserName(userId: String): MutableLiveData<String> {
        val userNameLiveData = MutableLiveData<String>()

         db.child("UserInfo").child(userId).child("firstName")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.getValue(String::class.java)
                    name?.let {
                        userNameLiveData.value = it
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
        return userNameLiveData
    }
}