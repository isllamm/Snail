package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName
import java.io.File

data class ProfileBody(
    @SerializedName("user_id")
    val userId: Int,
    val name: String,
    @SerializedName("country_code")
    val countryCode: String,
    val phone: String,
    val email: String,
    val image: File?
)