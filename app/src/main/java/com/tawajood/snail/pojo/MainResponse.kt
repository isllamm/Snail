package com.tawajood.snail.pojo

data class MainResponse<T>(
    val status: Boolean,
    val msg: String,
    val data: T?
)