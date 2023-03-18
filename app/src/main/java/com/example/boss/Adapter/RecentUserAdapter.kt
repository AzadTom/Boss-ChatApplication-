package com.example.boss.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.boss.Model.Recenuser
import com.example.boss.R
import java.util.*

class RecentUserAdapter(private val listenr:onRecentItemClickListner) :ListAdapter<Recenuser,RecentUserAdapter.getViewholder>(diffutil) {


    object diffutil: DiffUtil.ItemCallback<Recenuser>(){
        override fun areItemsTheSame(oldItem: Recenuser, newItem: Recenuser): Boolean {


            return oldItem.useruid == newItem.useruid
        }

        override fun areContentsTheSame(oldItem: Recenuser, newItem: Recenuser): Boolean {

            return oldItem == newItem
        }


    }
    inner class getViewholder(view:View):ViewHolder(view)
    {
        val userimg = view.findViewById<ImageView>(R.id.user_img)
        val username =view.findViewById<TextView>(R.id.name)
        val useremail = view.findViewById<TextView>(R.id.useremail)
        val time =    view.findViewById<TextView>(R.id.time)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): getViewholder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recent_user_item,parent,false)
        return getViewholder(view)


    }

    override fun onBindViewHolder(holder: getViewholder, position: Int) {

         val list = getItem(position)

        holder.username.text = list.username
        holder.useremail.text = list.lastmessage?.message.toString()

        holder.time.text = list.lastmessage!!.date
        holder.userimg.load(list.userImg?.toUri())

       holder.itemView.setOnClickListener {


           listenr.onitem(list)


       }


    }
}

interface onRecentItemClickListner {

    fun onitem(recenuser: Recenuser)

}

