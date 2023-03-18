package com.example.boss.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.boss.R
import com.example.boss.databinding.FragmentGetStartedBinding


class GetStarted : Fragment() {



    private lateinit var binding: FragmentGetStartedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


       binding =  FragmentGetStartedBinding.inflate(inflater, container, false)


        binding.getstart.setOnClickListener {



           findNavController().navigate(R.id.action_getStarted_to_signUp)



        }




        return binding.root
    }


}