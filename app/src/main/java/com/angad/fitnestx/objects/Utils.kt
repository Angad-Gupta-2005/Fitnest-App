package com.angad.fitnestx.objects

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.angad.fitnestx.databinding.ProgressDialogBinding
import com.google.firebase.auth.FirebaseAuth

object Utils {


    private var dialog: AlertDialog? = null

    fun showDialog(context: Context, message: String){
        val progress = ProgressDialogBinding.inflate(LayoutInflater.from(context))
        progress.tvMessage.text = message
        dialog = AlertDialog.Builder(context).setView(progress.root).setCancelable(false).create()
        dialog!!.show()
    }

    fun hideDialog(){
        dialog?.dismiss()
    }

    //    Creating an instance of firebaseAuth and databaseReference
    private var auth: FirebaseAuth? = null
    fun getAuthInstance(): FirebaseAuth {
        if (auth == null){
            auth = FirebaseAuth.getInstance()
        }
        return auth!!
    }

    //    For current user uid
    fun getCurrentUserId(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

}