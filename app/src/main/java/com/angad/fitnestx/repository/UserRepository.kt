package com.angad.fitnestx.repository

import androidx.lifecycle.MutableLiveData
import com.angad.fitnestx.models.User_detail
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserRepository {

    private val db = FirebaseDatabase.getInstance("https://blinkit-clone-f610a-default-rtdb.asia-southeast1.firebasedatabase.app")
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

//    Function that fetch user weight and height
    fun fetchUserWeightAndHeight(userId: String): MutableLiveData<Pair<Float?, Float?>> {
        val userWeightAndHeight = MutableLiveData<Pair<Float?, Float?>>()

        db.child("UserPersonal").child(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val weight = snapshot.child("weight").getValue(Float::class.java)
                    val height = snapshot.child("height").getValue(Float::class.java)

                    // Post the fetched values as a pair
                    userWeightAndHeight.value = Pair(weight, height)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors here if needed
                    userWeightAndHeight.value = Pair(null, null) // Set to null on error
                }
            })

        return userWeightAndHeight
    }


}