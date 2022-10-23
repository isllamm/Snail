package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class RequestTypeResponse(
    @SerializedName("request_types")
    val types: MutableList<RequestTypes>,
)