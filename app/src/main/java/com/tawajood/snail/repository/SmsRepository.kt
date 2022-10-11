package com.tawajood.snail.repository


import com.tawajood.snail.api.SmsApi
import com.tawajood.snail.pojo.SmsBody
import com.tawajood.snail.utils.Constants
import javax.inject.Inject

class SmsRepository
@Inject
constructor(private val api: SmsApi) {

    suspend fun sendSms(smsBody: SmsBody) =
        api.sendSms(Constants.SMS_TOKEN, "application/json", smsBody)
}
