package com.tawajood.snail.api


import com.tawajood.snail.pojo.SmsBody
import com.tawajood.snail.pojo.SmsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SmsApi {
    companion object{
        const val BASE_URL = "https://api.oursms.com/msgs/"
    }

    @POST("sms")
    suspend fun sendSms(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Body smsBody: SmsBody
    ): Response<SmsResponse>
}