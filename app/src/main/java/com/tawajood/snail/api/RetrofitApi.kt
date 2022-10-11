package com.tawajood.snail.api


import com.tawajood.snail.pojo.*

import retrofit2.Response
import retrofit2.http.*

interface RetrofitApi {

    companion object {
        const val BASE_URL = "https://snail.horizon.net.sa/api/"
    }


    @POST("register")
    suspend fun register(
        @Header("lang") lang: String,
        @Body registerBody: RegisterBody
    ): Response<MainResponse<UserResponse>>

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Header("lang") lang: String,
        @Field("country_code") countryCode: String,
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Response<MainResponse<UserResponse>>


    @FormUrlEncoded
    @POST("check-phone")
    suspend fun checkPhone(
        @Header("lang") lang: String,
        @Field("country_code") countryCode: String,
        @Field("phone") phone: String
    ): Response<MainResponse<Exist>>

    @GET("most-rated-clinics")
    suspend fun getSlider(
        @Header("lang") lang: String
    ): Response<MainResponse<SliderResponse>>


    @FormUrlEncoded
    @POST("my-requests")
    suspend fun getMyConsultants(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Field("user_id") user_id: String,
    ): Response<MainResponse<ConsultantsResponse>>

    @GET("clinics")
    suspend fun getClinics(
        @Header("lang") lang: String
    ): Response<ClinicsResponse>

    @GET("user/profile")
    suspend fun getProfile(
        @Header("token") token: String,
        @Header("lang") lang: String,
    ): Response<MainResponse<ProfileResponse>>

    @FormUrlEncoded
    @POST("get-identifiers")
    suspend fun getIdentifiers(
        @Header("token") token: String,
        @Field("user_id") userId: Int
    ): Response<MainResponse<Identifier>>

    @GET("setting/terms")
    suspend fun getTerms(
        @Header("lang") lang: String
    ): Response<MainResponse<Terms>>

    @GET("setting/about-us")
    suspend fun getAbout(
        @Header("lang") lang: String
    ): Response<MainResponse<About>>

    @GET("setting/contact-us")
    suspend fun getContact(
        @Header("lang") lang: String,
    ): Response<MainResponse<ContactResponse>>
}