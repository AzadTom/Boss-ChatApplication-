package com.example.boss.Repositery


import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.boss.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Repositery {


    private var mauth: FirebaseAuth
    private var mstorage : StorageReference
    private var mdatabase: DatabaseReference

    init {


        mauth = Firebase.auth
        mstorage = Firebase.storage.reference
        mdatabase = Firebase.database.reference

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun signUp(email:String, password:String, updateUI : ()-> Unit, uri: Uri, username:String)
    {
        mauth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                uploadData(uri,username)
                updateUI()





            }


    }

     @RequiresApi(Build.VERSION_CODES.O)
     fun uploadData(uri: Uri, username: String) {

        val reference = mstorage.child("profile").child(Date().time.toString())

        reference.putFile(uri).addOnCompleteListener{

            if (it.isSuccessful)
            {

                reference.downloadUrl.addOnSuccessListener {task ->

                    uploadInfo(task.toString(),username)

                }
            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun uploadInfo(imgUrl: String, username: String) {

     // val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a")

        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        val current = LocalDateTime.now().format(formatter)

        val user = User(useruid = mauth.currentUser!!.uid, username = username, userImg = imgUrl,date =current.toString().uppercase(), usermail =mauth.currentUser!!.email.toString())

        mdatabase.child("users")
            .child(mauth.uid.toString())
            .setValue(user)




    }





    fun signIn(email:String ,password:String ,auth: FirebaseAuth , updateUI : ()-> Unit)
    {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                updateUI()


            }


    }


}