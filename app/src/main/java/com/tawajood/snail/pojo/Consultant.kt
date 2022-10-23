package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Consultant(
    val id: Int,
    val user_id: Int,
    val pet_id: Int,
    val clinic_id: Int,
    val clinic: Clinic,
    val specialization_id: Int,
    val specialization: String,
    val type_id: Int,
    val clinic_day_id: Int,
    val day: String,
    val clinic_time_id: Int,
    val times: Times,
    val details: String,
    val clinic_report: String,
    val lat: String,
    val lng: String,
    val accepted: Int,
    val paid: Int,
    val status: Int,
    val statusName: String,
    val created_at: String,
    val images: MutableList<ConsultantImages>,
    val requestType: RequestTypes

) : Serializable