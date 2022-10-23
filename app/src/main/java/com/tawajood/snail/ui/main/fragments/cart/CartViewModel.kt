package com.tawajood.snail.ui.main.fragments.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.pojo.CartResponse
import com.tawajood.snail.pojo.ClinicsResponse
import com.tawajood.snail.repository.Repository
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject
constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _getCartFlow = MutableStateFlow<Resource<CartResponse>>(Resource.Idle())
    val getCartFlow = _getCartFlow.asStateFlow()

    private val _deleteFlow = MutableSharedFlow<Resource<Any>>()
    val deleteFlow = _deleteFlow.asSharedFlow()

    private val _updateFlow = MutableSharedFlow<Resource<Any>>()
    val updateFlow = _updateFlow.asSharedFlow()

    init {
        getCart()
    }

    private fun getCart() = viewModelScope.launch {
        try {
            _getCartFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getCart())
            if (response.status) {
                _getCartFlow.emit(Resource.Success(response.data!!))
            } else {
                _getCartFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _getCartFlow.emit(Resource.Error(message = e.message!!))
        }
    }

    fun deleteItemFromCart(cartItemId: Int) = viewModelScope.launch {
        try {
            _deleteFlow.emit(Resource.Loading())
            val response = handleResponse(repository.deleteItemFromCart(cartItemId))
            if (response.status) {
                PrefsHelper.setCartCount(PrefsHelper.getCartCount()-1)
                _deleteFlow.emit(Resource.Success(response.data!!))
                getCart()
            } else {
                _deleteFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _deleteFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }

    fun updateCart(cartItemId: Int, quantity: Int) = viewModelScope.launch {
        try {
            _updateFlow.emit(Resource.Loading())
            val response = handleResponse(repository.updateCart(cartItemId, quantity))
            if (response.status) {
                _updateFlow.emit(Resource.Success(response.data!!))
                getCart()
            } else {
                _updateFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _updateFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }
}