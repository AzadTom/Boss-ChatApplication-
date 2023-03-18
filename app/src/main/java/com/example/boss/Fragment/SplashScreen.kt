package com.example.boss.Fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.boss.R
import com.example.boss.databinding.FragmentSplashScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


class SplashScreen : Fragment() {


    private lateinit var binding: FragmentSplashScreenBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

       binding =  FragmentSplashScreenBinding.inflate(inflater, container, false)


        auth = FirebaseAuth.getInstance()
        val currentuser = auth.currentUser


        if (currentuser != null)
        {

            findNavController().navigate(R.id.action_splashScreen_to_homeScreen)

        }
        else{

            findNavController().navigate(R.id.action_splashScreen_to_getStarted)

        }






        return binding.root
    }


}