package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("profile")
    val profile: Profile
)
