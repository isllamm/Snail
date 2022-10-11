package com.tawajood.snail.utils


import android.util.Log
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.pojo.MainResponse
import retrofit2.Response

fun <T> handleResponse(response: Response<MainResponse<T>>): MainResponse<T> {
    Log.d("islam", "handleResponse: llll")
    return if (response.isSuccessful) {
        Log.d("islam", ": "+response.toString())
        if (response.body()!!.status) {
            MainResponse(
                true,
                response.body()!!.msg,
                response.body()!!.data
            )

        } else {
            MainResponse(
                false,
                response.body()!!.msg,
                response.body()!!.data
            )
        }
    } else {
        val message =
            when (response.code()) {
                in 400..499 -> {
                    if (PrefsHelper.getLanguage() == Constants.LANG)
                        "Bad Request"
                    else
                        "غير موجود"
                }
                in 500..599 -> {
                    if (PrefsHelper.getLanguage() == Constants.LANG)
                        "Our server is sick right now, please try again later."
                    else
                        "خطأ في مقدم الخدمة، من فضلك حاول في وقت لاحق."
                }
                else -> ""
            }
        MainResponse(
            false,
            message,
            null
        )
    }
}