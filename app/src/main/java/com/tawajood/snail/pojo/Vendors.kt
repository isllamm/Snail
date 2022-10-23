package com.tawajood.snail.pojo

data class Vendors(
    val current_page: Int,
    val data: MutableList<Store>,
    val first_page_url:String,
    )