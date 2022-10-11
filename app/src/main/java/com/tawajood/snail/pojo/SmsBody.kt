package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class SmsBody(
    val src: String,
    @SerializedName("dests")
    val dest: ArrayList<String>,
    val body: String
)
