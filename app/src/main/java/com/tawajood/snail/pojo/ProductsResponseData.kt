package com.tawajood.snail.pojo

data class ProductsResponseData (
    val current_page: Int,
    val data:MutableList<Product>,
)