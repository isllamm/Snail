package com.tawajood.snail.ui.login.fragments.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.pojo.*

import com.tawajood.snail.repository.Repository
import com.tawajood.snail.repository.SmsRepository
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject
constructor(
    private val repository: Repository,
    private val smsRepository: SmsRepository
): ViewModel(){



    private val _userFlow = MutableSharedFlow<Resource<User>>()
    val userFlow =  _userFlow.asSharedFlow()

    private val _sms = MutableSharedFlow<Resource<String>>()
    val sms = _sms.asSharedFlow()

    private val _checkPhone = MutableStateFlow<Resource<Exist>>(Resource.Idle())
    val checkPhone = _checkPhone.asSharedFlow()

    init {
    }


    fun register(registerBody: RegisterBody) = viewModelScope.launch {
        try {
            _userFlow.emit(Resource.Loading())
            val response = handleResponse(repository.register(registerBody))
            if (response.status) {
                _userFlow.emit(Resource.Success(response.data!!.user))
            } else {
                _userFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _userFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }


    fun checkPhone(countryCode: String, phone: String) = viewModelScope.launch {
        try {
            _checkPhone.emit(Resource.Loading())
            val response = handleResponse(repository.checkPhone(countryCode, phone))
            if (response.status) {
                _checkPhone.emit(Resource.Success(response.data!!))
            } else {
                _checkPhone.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _checkPhone.emit(Resource.Error(message = e.message!!))
        }
    }
}