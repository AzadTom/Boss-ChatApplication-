package com.example.boss.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.boss.Model.User
import com.example.boss.R

class AllUserAdapter(private val listener:onItemClicklistener): ListAdapter<User,AllUserAdapter.getViewholder>(diffutil){


    object diffutil: DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return  oldItem.useruid == newItem.useruid
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {

            return oldItem == newItem
        }


    }


    inner class getViewholder(view:View):RecyclerView.ViewHolder(view){

        val userimg = view.findViewById<ImageView>(R.id.user_img)
        val username =view.findViewById<TextView>(R.id.name)
        val useremail = view.findViewById<TextView>(R.id.useremail)
         val time =    view.findViewById<TextView>(R.id.time)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): getViewholder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false)
        return getViewholder(view)


    }


    override fun onBindViewHolder(holder: getViewholder, position: Int) {


        val list = getItem(position)

        holder.username.text = list.username
        holder.useremail.text = list.usermail
        holder.time.text = list.date
        holder.userimg.load(list.userImg.toUri())

        holder.itemView.setOnClickListener {
            listener.onitem(list)

        }





    }
}

interface onItemClicklistener {

    fun onitem(user: User)

}