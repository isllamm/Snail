package com.tawajood.snail.pojo

data class Product(
    val id: Int,
    val name: String,
    val desc: String,
    val subcat_id: Int,
    val price: Int,
    val image: String,
    val vendor_id: Int,
    val images: MutableList<ProductImages>
)