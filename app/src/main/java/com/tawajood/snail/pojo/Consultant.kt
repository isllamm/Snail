package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Consultant(
    val id: Int,
    val name: String,
    val phone: String,
    val rate: Float,
    val details: String,
    val email: String,
    val subtitle: String,
    val title: String,

): Serializable