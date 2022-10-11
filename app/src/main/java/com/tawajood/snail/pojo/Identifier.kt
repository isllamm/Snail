package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class Identifier(
    @SerializedName("has_notifications")
    var hasNotifications: Int,
    @SerializedName("has_orders")
    var hasOrders: Int,
    @SerializedName("has_special_orders")
    var hasSpecialOrders: Int,
    @SerializedName("has_messages")
    var hasMessages: Int,
    @SerializedName("has_offers")
    var hasOffers: Int
)