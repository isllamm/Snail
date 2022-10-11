package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    val id: Int,
    val address: String,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("fcm_token")
    val fcmToken: String?,
    val image: String,
    val lat: String,
    val lng: String,
    val name: String,
    val notifiable: Int,
    val phone: String,
    val token: String,
    val email: String,

    ) : Serializable