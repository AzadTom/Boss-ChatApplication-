package com.example.boss.Viewmodel

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boss.Model.User
import com.example.boss.Repositery.Repositery
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewmodel @Inject constructor(private val repositery: Repositery) :ViewModel() {







    @RequiresApi(Build.VERSION_CODES.O)
    fun SignUp(email:String, password :String, updateUI : ()->Unit, uri: Uri, username:String) = viewModelScope.launch(Dispatchers.IO) {


        repositery.signUp(email,password,updateUI,uri,username)

    }


    fun SignIn(auth: FirebaseAuth ,email:String,password :String,updateUI : ()->Unit) = viewModelScope.launch(Dispatchers.IO) {


        repositery.signIn(email,password,auth,updateUI)

    }





}