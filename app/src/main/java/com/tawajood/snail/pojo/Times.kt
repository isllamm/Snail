package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class Times
    (
    val id: Int,
    @SerializedName("clinic_day_id")
    val clinicDayId: Int,
    val from: String,
    val to: String,
)