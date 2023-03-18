package com.example.boss.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boss.Adapter.AllUserAdapter
import com.example.boss.Adapter.onItemClicklistener
import com.example.boss.Model.User
import com.example.boss.databinding.FragmentSearchActionBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class Search_action : Fragment(), onItemClicklistener {

    private lateinit var binding: FragmentSearchActionBinding

    private lateinit var list: ArrayList<User>
    private lateinit var database: DatabaseReference
    private lateinit var allUserAdapter: AllUserAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentSearchActionBinding.inflate(inflater, container, false)

        list = ArrayList()
        database = Firebase.database.reference

        allUser()
        search()



        return binding.root
    }

    private fun search() {

        binding.searchfromsearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                filter(s.toString())

            }


        })


    }


    private fun filter(text: String) {

        val temp: ArrayList<User> = ArrayList()

        for (i in list) {

            if (i.username.lowercase().contains(text.lowercase())) {
                temp.add(i)


            }

        }

        allUserAdapter.submitList(temp)


    }

    private fun allUser() {

        database.child("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                list.clear()

                for (user in snapshot.children) {

                    val muser = user.getValue(User::class.java)

                    if (muser!!.useruid != Firebase.auth.uid) {

                        list.add(muser)
                    }



                }



                allUserAdapter = AllUserAdapter(this@Search_action)
                binding.recylerview.adapter = allUserAdapter
                binding.recylerview.layoutManager = LinearLayoutManager(requireContext())
                allUserAdapter.submitList(list)


            }

            override fun onCancelled(error: DatabaseError) {


            }


        })

    }

    override fun onitem(user: User) {


        val action = Search_actionDirections.actionSearchActionToChatScreen(user)
        findNavController().navigate(action)


    }


}