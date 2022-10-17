package com.tawajood.snail.utils


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