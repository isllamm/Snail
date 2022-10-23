package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class ConsultantsResponse(
    @SerializedName("requests")
    val consultants: MutableList<Consultant>,

    )