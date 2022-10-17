package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName
import java.io.File

data class PetBody(
    @SerializedName("user_id")
    val user_id: Int,
    val name: String,
    val age: String,
    val weight: String,
    val gender: Int,
    val image: File?,
    val type_id: Int,
)