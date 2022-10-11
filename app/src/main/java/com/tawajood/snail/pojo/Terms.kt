package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class Terms(
    @SerializedName("term")
    val terms: String
)