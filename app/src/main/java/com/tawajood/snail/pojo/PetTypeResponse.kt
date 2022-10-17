package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class PetTypeResponse(
    @SerializedName("pet_types")
    val petTypes: MutableList<PetType>,
)