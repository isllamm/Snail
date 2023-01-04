package com.tawajood.snail.pojo

data class Review(
    val id: Int,
    val rate: Double,
    val comment: String,
    val user_id: Int,
    val user: User,
)