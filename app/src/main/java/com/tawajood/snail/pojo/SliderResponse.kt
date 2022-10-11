package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class SliderResponse(
    @SerializedName("clinics")
    val sliderItems: MutableList<Slider>
)
