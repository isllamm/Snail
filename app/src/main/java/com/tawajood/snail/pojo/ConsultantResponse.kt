package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class ConsultantResponse(
    @SerializedName("request")
    val consultant: Consultant,

    )