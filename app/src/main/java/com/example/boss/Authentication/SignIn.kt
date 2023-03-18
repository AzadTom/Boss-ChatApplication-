package com.example.boss.Authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.boss.R
import com.example.boss.Viewmodel.ChatViewmodel
import com.example.boss.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SignIn : Fragment() {

  private lateinit var binding: FragmentSignInBinding
  private lateinit var auth: FirebaseAuth

  @Inject
  lateinit var viewmodel: ChatViewmodel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding =  FragmentSignInBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        binding.SignIn.setOnClickListener {

            signIn(it)

        }

       binding.SignUp.setOnClickListener {


           findNavController().navigate(R.id.action_signIn_to_signUp)


       }



        return binding.root
    }

    private fun signIn(view: View) {

        binding.SignIn.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE

        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        if( email.isNotEmpty() && password.isNotEmpty())
        {

            viewmodel.SignIn(auth,email,password){

                Toast.makeText(requireContext(),"Successfully SignIn!", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_signIn_to_homeScreen)

            }


        }
        else
        {

            Toast.makeText(requireContext(),"Please fill detail carefully!", Toast.LENGTH_SHORT).show()
        }

    }


}