package com.tawajood.snail.ui.main.fragments.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.pojo.CartResponse
import com.tawajood.snail.pojo.OrderBody
import com.tawajood.snail.repository.Repository
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject
constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _addOrderFlow = MutableStateFlow<Resource<Any>>(Resource.Idle())
    val addOrderFlow = _addOrderFlow.asStateFlow()

    private val _cartFlow = MutableStateFlow<Resource<CartResponse>>(Resource.Idle())
    val cartFlow = _cartFlow.asStateFlow()

    init {
        getCart()
    }

    fun addOrder(orderBody: OrderBody) = viewModelScope.launch {
        try {
            _addOrderFlow.emit(Resource.Loading())
            val response = handleResponse(repository.addOrder(orderBody))
            if (response.status) {
                _addOrderFlow.emit(Resource.Success(response.data!!))
            } else {
                _addOrderFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _addOrderFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }

    private fun getCart() = viewModelScope.launch {
        try {
            _cartFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getCart())
            if (response.status) {
                _cartFlow.emit(Resource.Success(response.data!!))
            } else {
                _cartFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _cartFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }

}