package com.tawajood.snail.pojo

data class CartResponse(
    val carts: MutableList<Cart>,
    val total: String,
    val tax: String,
    val delivery_cost: String,
    val finalTotal: String,
)