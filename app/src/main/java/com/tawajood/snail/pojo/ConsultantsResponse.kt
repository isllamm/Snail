package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class ConsultantsResponse(
    @SerializedName("my_requests")
    val consultants: MutableList<Consultant>,

    )