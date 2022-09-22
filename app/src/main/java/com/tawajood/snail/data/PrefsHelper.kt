package com.tawajood.snail.data

import android.content.Context
import android.content.SharedPreferences
import com.tawajood.snail.utils.Constants

object PrefsHelper {

    private lateinit var preferences: SharedPreferences
    private const val PREFS_NAME = "shared_prefs"

    fun init(context: Context){
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun setLanguage(language: String) {
        preferences.edit().putString(Constants.LANG, language).apply()
    }

    fun getLanguage(): String {
        return preferences.getString(Constants.LANG, Constants.AR).toString()
    }

    fun setFirst(isFirst: Boolean){
        preferences.edit().putBoolean(Constants.IS_FIRST, isFirst).apply()
    }

    fun isFirst(): Boolean{
        return preferences.getBoolean(Constants.IS_FIRST, true)
    }

    fun setToken(token: String){
        preferences.edit().putString(Constants.TOKEN, token).apply()
    }

    fun getToken(): String{
        return preferences.getString(Constants.TOKEN, "").toString()
    }

    fun setFCMToken(token: String){
        preferences.edit().putString(Constants.FCM_TOKEN, token).apply()
    }

    fun getFCMToken(): String{
        return preferences.getString(Constants.FCM_TOKEN, "").toString()
    }

    fun setUserId(id: Int) {
        preferences.edit().putInt(Constants.USER_ID, id).apply()
    }

    fun getUserId(): Int{
        return preferences.getInt(Constants.USER_ID, -1)
    }

    fun setUsername(name: String) {
        preferences.edit().putString(Constants.USERNAME, name).apply()
    }

    fun getUsername(): String{
        return preferences.getString(Constants.USERNAME, "")!!
    }

    fun setPhone(phone: String) {
        preferences.edit().putString(Constants.PHONE, phone).apply()
    }

    fun getPhone(): String{
        return preferences.getString(Constants.PHONE, "")!!
    }

    fun setCountryCode(code: String) {
        preferences.edit().putString(Constants.COUNTRY_CODE, code).apply()
    }

    fun getCountryCode(): String{
        return preferences.getString(Constants.COUNTRY_CODE, "")!!
    }

    fun setUserImage(code: String) {
        preferences.edit().putString(Constants.USER_IMAGE, code).apply()
    }

    fun getUserImage(): String{
        return preferences.getString(Constants.USER_IMAGE, "")!!
    }

}