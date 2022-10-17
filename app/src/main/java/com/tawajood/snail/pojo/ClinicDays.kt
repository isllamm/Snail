package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class ClinicDays(
    val id: Int,
    @SerializedName("clinic_id")
    val clinicId: Int,
    @SerializedName("day_id")
    val dayId: Int,
    val day :Day,
    val times:MutableList<Times>,
)