package com.example.boss.Authentication

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.boss.Model.User
import com.example.boss.R
import com.example.boss.Viewmodel.ChatViewmodel
import com.example.boss.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SignUp : Fragment() {


    private lateinit var binding: FragmentSignUpBinding
    private lateinit var uri: Uri

    @Inject
    lateinit var viewModel: ChatViewmodel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.SignUp.setOnClickListener {


            signUp(it)

        }


        binding.selectImg.setOnClickListener {

            setImage()

        }





        binding.SignIn.setOnClickListener {



            findNavController().navigate(R.id.action_signUp_to_signIn)



        }

        return binding.root
    }

    private fun setImage() {


        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent, 101)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {

            if (data.data != null) {

                uri = data.data!!

                binding.userImg.setImageURI(uri)

            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun signUp(view: View) {

        loading()
        val username = binding.username.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val comfirmpassword = binding.comfirmpassword.text.toString()

        if (password == comfirmpassword && email.isNotEmpty() && password.isNotEmpty() && comfirmpassword.isNotEmpty()) {

            viewModel.SignUp( email = email, password =  password, uri = uri, username = username, updateUI = {

                Toast.makeText(requireContext(), "Successfully SignUp!", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_signUp_to_homeScreen)

            })


        } else {

            Toast.makeText(requireContext(), "Please fill detail carefully!", Toast.LENGTH_SHORT)
                .show()
        }


    }

    private fun loading() {
        binding.SignUp.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE


    }


}