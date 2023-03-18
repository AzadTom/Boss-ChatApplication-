package com.example.boss.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
    val useruid:String = "" ,
    val username:String = "",
    val userImg:String ="",
    val date:String ="",
    val usermail:String ="",
):Parcelable