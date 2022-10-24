package com.tawajood.snail.ui.main.fragments.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.pojo.CartResponse
import com.tawajood.snail.pojo.OrderResponse
import com.tawajood.snail.pojo.OrdersResponse
import com.tawajood.snail.repository.Repository
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject
constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _getOrdersFlow = MutableStateFlow<Resource<OrdersResponse>>(Resource.Idle())
    val getOrdersFlow = _getOrdersFlow.asStateFlow()

    private val _orderFlow = MutableStateFlow<Resource<OrderResponse>>(Resource.Idle())
    val orderFlow = _orderFlow.asStateFlow()

    private val _orderDeliveredFlow = MutableStateFlow<Resource<Any>>(Resource.Idle())
    val orderDeliveredFlow = _orderDeliveredFlow.asStateFlow()

    init {
        getOrders()
    }

    private fun getOrders() = viewModelScope.launch {
        try {
            _getOrdersFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getOrders())
            if (response.status) {
                _getOrdersFlow.emit(Resource.Success(response.data!!))
            } else {
                _getOrdersFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _getOrdersFlow.emit(Resource.Error(message = e.message!!))
        }
    }

    fun getOrderById(orderId: String) = viewModelScope.launch {
        try {
            _orderFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getOrderById(orderId))
            if (response.status) {
                _orderFlow.emit(Resource.Success(response.data!!))
            } else {
                _orderFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _orderFlow.emit(Resource.Error(message = e.message!!))
        }
    }

    fun orderDelivered(orderId: String) = viewModelScope.launch {
        try {
            _orderDeliveredFlow.emit(Resource.Loading())
            val response = handleResponse(repository.orderDelivered(orderId))
            if (response.status) {
                _orderDeliveredFlow.emit(Resource.Success(response.data!!))
            } else {
                _orderDeliveredFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _orderDeliveredFlow.emit(Resource.Error(message = e.message!!))
        }
    }
}