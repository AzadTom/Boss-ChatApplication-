package com.example.boss.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.boss.Adapter.AllUserAdapter
import com.example.boss.Adapter.onItemClicklistener
import com.example.boss.Model.User
import com.example.boss.R
import com.example.boss.databinding.FragmentAlluserBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class Alluser : Fragment() ,onItemClicklistener {


    private lateinit var binding: FragmentAlluserBinding
    private lateinit var allUserAdapter: AllUserAdapter
    private  var  alluser :ArrayList<User> = ArrayList()
    private lateinit var databaseReference :DatabaseReference



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding =  FragmentAlluserBinding.inflate(inflater, container, false)

         databaseReference = Firebase.database.reference
         allUser()


        binding.search.setOnClickListener {

            findNavController().navigate(R.id.action_alluser_to_search_action)


        }

        return binding.root
    }

    private fun allUser(){

        databaseReference.child("users").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                alluser.clear()

                for (user in snapshot.children)
                {

                    val muser =user.getValue(User::class.java)


                    if (muser!!.useruid != Firebase.auth.currentUser!!.uid)
                    {


                        alluser.add(muser)

                    }










                }



                allUserAdapter = AllUserAdapter(this@Alluser)

                binding.recylerview.adapter = allUserAdapter
                allUserAdapter.submitList(alluser)
                binding.recylerview.visibility = View.VISIBLE
                binding.alluserProgress.visibility = View.GONE






            }

            override fun onCancelled(error: DatabaseError) {



            }


        })

    }

    override fun onitem(user: User) {

        val action = AlluserDirections.actionAlluserToChatScreen(user)
        findNavController().navigate(action)



    }


}