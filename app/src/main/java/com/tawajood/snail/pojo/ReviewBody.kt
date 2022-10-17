package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class ReviewBody (
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("clinic_id")
    val clinicId: String,
    @SerializedName("rate")
    val rating: String,
    @SerializedName("comment")
    val comment: String,

        )