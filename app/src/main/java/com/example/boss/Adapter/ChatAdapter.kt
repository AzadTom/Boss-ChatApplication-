package com.example.boss.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.boss.Model.MessagePool
import com.example.boss.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChatAdapter:ListAdapter<MessagePool,ViewHolder>(diffutil) {


    var ITEMSENT =1
    var ITEMRECEIVER =2


    object diffutil: DiffUtil.ItemCallback<MessagePool>(){
        override fun areItemsTheSame(oldItem: MessagePool, newItem: MessagePool): Boolean {

            return oldItem.random == newItem.random
        }

        override fun areContentsTheSame(oldItem: MessagePool, newItem: MessagePool): Boolean {

            return  oldItem == newItem
        }

    }

    inner class senderViewholder(itemview:View): ViewHolder(itemview)
    {

        val messagestamp = itemview.findViewById<TextView>(R.id.sendermessagestamp)
        val timestamp = itemview.findViewById<TextView>(R.id.sendertimestamp)


    }

    inner class receiverViewholder(itemview:View): ViewHolder(itemview)
    {
        val messagestamp = itemview.findViewById<TextView>(R.id.receivermessagestamp)
        val timestamp = itemview.findViewById<TextView>(R.id.receivertimestamp)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        if (viewType == ITEMSENT)
        {
            return  senderViewholder(LayoutInflater.from(parent.context).inflate(R.layout.sender_item,parent,false))

        }
        else
        {

            return receiverViewholder(LayoutInflater.from(parent.context).inflate(R.layout.receiver_item,parent,false))

        }



    }

    override fun getItemViewType(position: Int): Int {

        return if (Firebase.auth.uid == getItem(position).uid) ITEMSENT else ITEMRECEIVER

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val message = getItem(position)

        if (holder.itemViewType ==ITEMSENT)
        {

            val viewHolder = holder as senderViewholder
            viewHolder.messagestamp.text = message.message
            viewHolder.timestamp.text = message.date

        }
        else
        {


            val viewHolder = holder as receiverViewholder
            viewHolder.messagestamp.text = message.message
            viewHolder.timestamp.text = message.date
        }

    }


}