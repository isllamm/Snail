package com.tawajood.snail.pojo

data class PetVaccinations(
    val id: Int,
    val pet_id: Int,
    val date: String,
    val type_id: Int,
    val vaccinationType:VaccinationTypes
)