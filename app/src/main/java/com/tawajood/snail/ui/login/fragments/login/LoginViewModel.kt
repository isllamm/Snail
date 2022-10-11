package com.tawajood.snail.ui.login.fragments.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.pojo.User
import com.tawajood.snail.repository.Repository
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {

    private val _userFlow = MutableSharedFlow<Resource<User>>()
    val userFlow = _userFlow.asSharedFlow()

    fun login(countryCode: String, phone: String, password: String) = viewModelScope.launch {
        try {
            _userFlow.emit(Resource.Loading())
            val response = handleResponse(repository.login(countryCode, phone, password))
            if (response.status) {
                _userFlow.emit(Resource.Success(response.data!!.user))
            } else {
                _userFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _userFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }
}