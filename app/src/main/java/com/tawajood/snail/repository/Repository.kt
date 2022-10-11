package com.tawajood.snail.repository


import android.util.Log
import com.tawajood.snail.api.RetrofitApi
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.pojo.RegisterBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class Repository
@Inject
constructor(private val api: RetrofitApi) {

    suspend fun getIdentifiers() =
        api.getIdentifiers(PrefsHelper.getToken(), PrefsHelper.getUserId())

    suspend fun register(registerBody: RegisterBody) =
        api.register(PrefsHelper.getLanguage(), registerBody)

    suspend fun login(countryCode: String, phone: String, password: String) =
        api.login(PrefsHelper.getLanguage(), countryCode, phone, password)

    suspend fun checkPhone(countryCode: String, phone: String) =
        api.checkPhone(PrefsHelper.getLanguage(), countryCode, phone)

    suspend fun getSlider() = api.getSlider(PrefsHelper.getLanguage())

    suspend fun getClinics() = api.getClinics(PrefsHelper.getLanguage())

    suspend fun getProfile() = api.getProfile(PrefsHelper.getToken(), PrefsHelper.getLanguage())
    suspend fun getTerms() = api.getTerms(PrefsHelper.getLanguage())
    suspend fun getAbout() = api.getAbout(PrefsHelper.getLanguage())
    suspend fun getContact() = api.getContact(PrefsHelper.getLanguage())
    suspend fun getConsultants() = api.getMyConsultants(
        PrefsHelper.getToken(),
        PrefsHelper.getLanguage(),
        PrefsHelper.getUserId().toString()
    )


}