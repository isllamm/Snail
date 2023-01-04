package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class Clinic(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String,
    val image: String,
    val country_code: String,
    val lat: Double?,
    val lng: Double?,
    val image_clinic: String,
    val address: String,
    val details: String?,
    val registration_number: String,
    val fcm_token: String,
    val mobile_id: String,
    val active: Int,
    val notifiable: Int,
    val token: String?,
    val locale: String,
    val rating: Double,
    val specializations: MutableList<Specializations>,
    @SerializedName("clinic_days")
    val clinicDays: MutableList<ClinicDays>,
    @SerializedName("clinic_images")
    val clinicImages: MutableList<ImagesClinic>,
    val recommendations: MutableList<Review>,
)
