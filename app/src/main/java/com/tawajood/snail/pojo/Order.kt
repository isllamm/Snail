package com.tawajood.snail.pojo

data class Order(
    val id: Int,
    val user_id: Int,
    val user_phone: String,
    val country_code: String,
    val status: Int,
    val statusName: String,
    val total: Int,
    val tax: Int,
    val delivery_cost: Int,
    val discount: Int,
    val final_total: Int,
    val lat: String,
    val lng: String,
    val address: String,
    val coupon_code: String,
    val carts: MutableList<Cart>?,
    val created_at: String,
    val name: String,
    val payment_method: Int,
)