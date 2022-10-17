package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("success")
    val profile: Profile
)
