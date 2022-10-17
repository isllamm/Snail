package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class Profile(
    val id: Int,
    val active: Int,
    val address: String,
    val email: String,
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
)