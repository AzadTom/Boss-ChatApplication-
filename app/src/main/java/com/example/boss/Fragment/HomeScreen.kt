package com.example.boss.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.boss.Adapter.RecentUserAdapter
import com.example.boss.Adapter.onRecentItemClickListner
import com.example.boss.Model.Recenuser
import com.example.boss.Model.User
import com.example.boss.R
import com.example.boss.databinding.FragmentHomeScreenBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class HomeScreen : Fragment() ,onRecentItemClickListner {


    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var database :DatabaseReference
    private lateinit var recentlist:ArrayList<Recenuser>
    private  lateinit var recentUserAdapter: RecentUserAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =  FragmentHomeScreenBinding.inflate(inflater, container, false)

        database = Firebase.database.reference
        recentlist = ArrayList()
        recentUserAdapter = RecentUserAdapter(this)



        binding.searchfromhome.setOnClickListener {

            findNavController().navigate(R.id.action_homeScreen_to_search_action)

        }

        binding.floatingActionButton.setOnClickListener {

            findNavController().navigate(R.id.action_homeScreen_to_alluser)

        }




         allRecentUser()


        return binding.root
    }


    private fun allRecentUser(){




        database.child("recent").child(Firebase.auth.currentUser!!.uid).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                recentlist.clear()

                for (user in snapshot.children)
                {

                    val recenuser = user.getValue(Recenuser::class.java)

                    if (recenuser!!.useruid != Firebase.auth.currentUser!!.uid)
                    {
                        recentlist.add(recenuser)
                    }



                }



                    binding.recylerview.adapter = recentUserAdapter
                    recentUserAdapter.submitList(recentlist.distinct())
                    binding.recylerview.visibility = View.VISIBLE
                    binding.homeProgress.visibility = View.GONE













            }

            override fun onCancelled(error: DatabaseError) {



            }


        })

    }



    override fun onitem(recenuser: Recenuser) {

         val user = User(recenuser.useruid.toString() , recenuser.username.toString(),recenuser.userImg.toString(),recenuser.lastmessage?.date.toString(),recenuser.usermail.toString())

        val action = HomeScreenDirections.actionHomeScreenToChatScreen(user)

        findNavController().navigate(action)


    }


}