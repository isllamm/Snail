package com.tawajood.snail.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.pojo.Identifier
import com.tawajood.snail.repository.Repository
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val repository: Repository
): ViewModel(){

    private val _cartCount = MutableLiveData<Int>()
    val cartCount: LiveData<Int> = _cartCount

    private val _latLng = MutableLiveData<LatLng>()
    val latLng: LiveData<LatLng> = _latLng

    private val _identifiers = MutableStateFlow<Resource<Identifier>>(Resource.Idle())
    val identifiers = _identifiers.asStateFlow()

    init {
        if (PrefsHelper.getToken().isNotEmpty()) {
            getIdentifiers()
        }
    }


    fun getIdentifiers() = viewModelScope.launch {
        try {
            _identifiers.emit(Resource.Loading())
            val response = handleResponse(repository.getIdentifiers())
            if (response.status) {
                _identifiers.emit(Resource.Success(response.data!!))
            } else {
                _identifiers.emit(Resource.Error(message = response.msg))
            }
        } catch (ex: CancellationException) {
            Log.d("islam", "exception: ${ex.message.toString()}")
        } catch (e: Exception) {
            _identifiers.emit(Resource.Error(message = e.message.toString()))
        }
    }

}