package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class OrderBody(
    @SerializedName("user_id")
    val userId: String,
    val name: String,
    @SerializedName("user_phone")
    val phone: String,
    @SerializedName("country_code")
    val countryCode: String,
    val address: String,
    val payment_method: String,
    var lat: Float = -1f,
    var lng: Float = -1f,
)