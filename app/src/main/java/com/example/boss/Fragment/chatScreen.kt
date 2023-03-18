package com.example.boss.Fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.boss.Adapter.ChatAdapter
import com.example.boss.Model.MessagePool
import com.example.boss.Model.Recenuser
import com.example.boss.Model.User
import com.example.boss.databinding.FragmentChatScreenBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class chatScreen : Fragment() {



    private lateinit var binding: FragmentChatScreenBinding
   private val args by navArgs<chatScreenArgs>()
    private lateinit var database :FirebaseDatabase
    private lateinit var list: ArrayList<MessagePool>
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var senderRoom :String
    private lateinit var receiverRoom:String
    private lateinit var sender :String
    private lateinit var receiver:String
    private var currentUser:User = User()



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding=  FragmentChatScreenBinding.inflate(inflater, container, false)

        list = ArrayList()
        chatAdapter = ChatAdapter()

          database = Firebase.database
          sender = Firebase.auth.currentUser!!.uid
          receiver = args.chatuser.useruid


           senderRoom = sender+receiver
           receiverRoom = receiver+sender


        args.chatuser
            binding.receiverImg.load(args.chatuser.userImg.toUri())
            binding.receivername.text = args.chatuser.username
            binding.receivermail.text =args.chatuser.usermail




        binding.right.setOnClickListener {

            findNavController().popBackStack()


        }

        binding.more.setOnClickListener {

            Toast.makeText(requireContext(),"More option!",Toast.LENGTH_SHORT).show()

        }

        binding.sendMessage.setOnClickListener {


           if (binding.messageStamp.text.isNotEmpty())
           {
               sendMessage()


           }
            else
           {

               Toast.makeText(requireContext(),"please fill it properly!",Toast.LENGTH_SHORT).show()
           }



        }


        updateList()
        return binding.root
    }

    private fun setRecylerView() {


       if(list.size >0)
       {
           binding.progressBar3.visibility = View.VISIBLE
           binding.recyclerView.adapter = chatAdapter
           binding.recyclerView.smoothScrollToPosition(list.size-1)
           chatAdapter.submitList(list)
           binding.recyclerView.visibility = View.VISIBLE
           binding.progressBar3.visibility = View.GONE



       }



    }


    private fun updateList() {

        database.reference.child("chats").child(senderRoom).child("message")
            .addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    list.clear()

                    for (messaging in snapshot.children)
                    {
                        val message = messaging.getValue(MessagePool::class.java)

                        list.add(message!!)


                    }


                    setRecylerView()


                }

                override fun onCancelled(error: DatabaseError) {


                    Toast.makeText(requireContext(),error.message,Toast.LENGTH_SHORT).show()


                }


            })


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendMessage() {

        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        val current = LocalDateTime.now().format(formatter)

        val message = MessagePool(binding.messageStamp.text.toString(),sender,current.toString(), Date().time.toString())

         database.reference.child("chats").child(senderRoom).child("message").push().setValue(message)
             .addOnSuccessListener {



                 database.reference.child("chats").child(receiverRoom).child("message").push().setValue(message).addOnSuccessListener {


                     binding.messageStamp.text = null
                     recentUser(args.chatuser.useruid ,args.chatuser.username ,args.chatuser.userImg ,args.chatuser.usermail)
                     Toast.makeText(requireContext(),"Message sent!",Toast.LENGTH_SHORT).show()

                 }

             }





    }


    private fun recentUser(uid :String ,uname:String ,uimage:String,uemail:String){



        database.reference.child("users").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                for (result in snapshot.children)
                {
                    val user = result.getValue(User::class.java)!!

                    if (user.useruid == Firebase.auth.currentUser!!.uid)
                    {
                        currentUser = User(user.useruid,user.username,user.userImg,user.date,user.usermail)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {


            }


        })



        database.reference.child("chats").child(senderRoom).child("message").orderByKey().limitToLast(1)
            .addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {


                    for (last in snapshot.children)
                    {

                        val recenuser = Recenuser(uid,uname,uimage,uemail,last.getValue(MessagePool::class.java))
                        val recenuser2 = Recenuser(currentUser.useruid,currentUser.username,currentUser.userImg,currentUser.usermail,last.getValue(MessagePool::class.java))

                        database.reference.child("recent")
                            .child(Firebase.auth.currentUser!!.uid)
                            .child(senderRoom)
                            .setValue(recenuser)
                            .addOnSuccessListener {

                                database.reference.child("recent")
                                    .child(uid)
                                    .child(receiverRoom)
                                    .setValue(recenuser2)


                            }


                    }


                }

                override fun onCancelled(error: DatabaseError) {


                    Toast.makeText(requireContext(),error.message,Toast.LENGTH_SHORT).show()

                }


            })




    }


}