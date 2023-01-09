package com.tawajood.snail.utils


import com.tawajood.snail.pojo.AddRequestBody
import com.tawajood.snail.pojo.PetBody
import com.tawajood.snail.pojo.ProfileBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


fun ProfileBody.toMap(): Map<String, RequestBody> {

    val updateMap = hashMapOf<String, RequestBody>()

    updateMap["user_id"] =
        userId.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    updateMap["name"] = name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    updateMap["country_code"] = countryCode.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    updateMap["phone"] = phone.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    updateMap["email"] = email.toRequestBody("multipart/form-data".toMediaTypeOrNull())

    return updateMap
}

fun PetBody.toMap(): Map<String, RequestBody> {
    val addPet = hashMapOf<String, RequestBody>()
    addPet["user_id"] =
        user_id.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    addPet["name"] =
        name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    addPet["age"] =
        age.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    addPet["weight"] =
        weight.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    addPet["gender"] =
        gender.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    addPet["type_id"] =
        type_id.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    return addPet
}

fun AddRequestBody.toMap(): Map<String, RequestBody> {
    val addRequest = hashMapOf<String, RequestBody>()
    addRequest["user_id"] =
        user_id.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    addRequest["clinic_id"] =
        clinic_id.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    addRequest["pet_id"] =
        pet_id.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    addRequest["specialization_id"] =
        specialization_id.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    addRequest["type_id"] =
        type_id.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    addRequest["details"] =
        details.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    addRequest["clinic_day_id"] =
        clinic_day_id.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    addRequest["clinic_time_id"] =
        clinic_time_id.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    addRequest["address"] =
        address!!.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    addRequest["lat"] =
        lat.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    addRequest["lng"] =
        lng.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())

    return addRequest
}