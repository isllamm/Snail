package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class Notification(
    val title: String,
    val body: String,
    @SerializedName("created_at")
    val date: String,
    @SerializedName("order_id")
    val orderId: Int?,
    @SerializedName("special_order_id")
    val specialOrderId: Int?,
    val image: String? = ""
)
