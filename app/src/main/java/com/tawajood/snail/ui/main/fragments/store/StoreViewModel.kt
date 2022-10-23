package com.tawajood.snail.ui.main.fragments.store

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.pojo.*
import com.tawajood.snail.repository.Repository
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {
    private val _categoriesFlow = MutableStateFlow<Resource<CategoriesResponse>>(Resource.Idle())
    val categoriesFlow = _categoriesFlow.asStateFlow()

    private val _addToCartFlow = MutableStateFlow<Resource<Any>>(Resource.Idle())
    val addToCartFlow = _addToCartFlow.asStateFlow()

    private val _vendorsFlow =
        MutableStateFlow<Resource<GetVendorsByCategoryIdResponse>>(Resource.Idle())
    val vendorsFlow = _vendorsFlow.asStateFlow()

    private val _productsFlow =
        MutableStateFlow<Resource<ProductsResponse>>(Resource.Idle())
    val productsFlow = _productsFlow.asStateFlow()

    private val _productByIdFlow =
        MutableStateFlow<Resource<ProductResponse>>(Resource.Idle())
    val productByIdFlow = _productByIdFlow.asStateFlow()

    private val _subCategoriesFlow =
        MutableStateFlow<Resource<SubCategoriesResponse>>(Resource.Idle())
    val subCategoriesFlow = _subCategoriesFlow.asStateFlow()

    init {
        getCategories()
    }

    fun addToCart(productId: String, quantity: String) = viewModelScope.launch {
        try {
            _addToCartFlow.emit(Resource.Loading())
            val response = handleResponse(repository.addToCart(productId, quantity))
            if (response.status) {
                _addToCartFlow.emit(Resource.Success(response.data!!))
            } else {
                _addToCartFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _addToCartFlow.emit(Resource.Error(message = e.message!!))
        }
    }

    private fun getCategories() = viewModelScope.launch {
        try {
            _categoriesFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getCategories())
            if (response.status) {
                _categoriesFlow.emit(Resource.Success(response.data!!))
            } else {
                _categoriesFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _categoriesFlow.emit(Resource.Error(message = e.message!!))
        }
    }

    fun getVendorsByCategoryId(catId: String) = viewModelScope.launch {
        try {
            _vendorsFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getVendorsByCategoryId(catId))
            if (response.status) {
                _vendorsFlow.emit(Resource.Success(response.data!!))
            } else {
                _vendorsFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _vendorsFlow.emit(Resource.Error(message = e.message!!))
        }
    }

    fun getSubCategory(vendorId: String) = viewModelScope.launch {
        try {
            _subCategoriesFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getSubCategories(vendorId))
            if (response.status) {
                _subCategoriesFlow.emit(Resource.Success(response.data!!))
            } else {
                _subCategoriesFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _subCategoriesFlow.emit(Resource.Error(message = e.message!!))
        }


    }

    fun getProducts(subcategoryId: String) = viewModelScope.launch {
        try {
            _productsFlow.emit(Resource.Loading())

            val response = handleResponse(repository.getProducts(subcategoryId))

            if (response.status) {
                _productsFlow.emit(Resource.Success(response.data!!))
            } else {
                _productsFlow.emit(Resource.Error(message = response.msg))

            }
        } catch (e: Exception) {
            Log.d("islam", "getProducts: " + e.message.toString())
            _productsFlow.emit(Resource.Error(message = e.message!!))
        }
    }

    fun getProductById(id: String) = viewModelScope.launch {
        try {
            _productByIdFlow.emit(Resource.Loading())

            val response = handleResponse(repository.getProductById(id))

            if (response.status) {
                _productByIdFlow.emit(Resource.Success(response.data!!))
            } else {
                _productByIdFlow.emit(Resource.Error(message = response.msg))

            }
        } catch (e: Exception) {
            Log.d("islam", "getProducts: " + e.message.toString())
            _productByIdFlow.emit(Resource.Error(message = e.message!!))
        }
    }
}
