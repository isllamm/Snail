package com.tawajood.snail.pojo

import com.google.gson.annotations.SerializedName

data class VaccinationTypesResponse(
    @SerializedName("vaccination_types")
    val vaccinationTypes: MutableList<VaccinationTypes>,
)