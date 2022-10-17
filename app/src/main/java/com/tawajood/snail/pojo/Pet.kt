package com.tawajood.snail.pojo

data class Pet(
    val id: Int,
    val user_id: Int,
    val name: String,
    val age: Int,
    val weight: Int,
    val gender: Int,
    val image: String,
    val type_id: Int,
    val type: String,
)