package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegisterBody(
    var name: String,
    @SerializedName("country_code")
    var countryCode: String,
    var phone: String,
    var email: String,
    var password: String,

): Serializable
