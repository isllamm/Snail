package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class Contact(
    val id: Int,
    val email: String,
    val phone: String,
    val website: String,
    val complaint_email: String,
    val suggestion_email: String,
    @SerializedName("whatsapp_number")
    val whatsapp: String,
    val twitter: String,
    val instagram: String,
    val facebook: String,
    val snapchat: String,
    val telegram: String
)