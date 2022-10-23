package com.tawajood.snail.ui.main.fragments.make_reservation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.pojo.*
import com.tawajood.snail.repository.Repository
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject
constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _myPetsFlow = MutableStateFlow<Resource<PetResponse>>(Resource.Idle())
    val myPetsFlow = _myPetsFlow.asSharedFlow()

    private val _clinicInfoFlow = MutableStateFlow<Resource<ClinicsResponse>>(Resource.Idle())
    val clinicInfoFlow = _clinicInfoFlow.asStateFlow()

    private val _requestTypesFlow = MutableStateFlow<Resource<RequestTypeResponse>>(Resource.Idle())
    val requestTypesFlow = _requestTypesFlow.asSharedFlow()

    private val _addRequestFlow = MutableStateFlow<Resource<Any>>(Resource.Idle())
    val addRequestFlow = _addRequestFlow.asSharedFlow()

    fun getPets() = viewModelScope.launch {
        try {
            _myPetsFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getPets())

            if (response.status) {
                _myPetsFlow.emit(Resource.Success(response.data!!))
            } else {
                _myPetsFlow.emit(Resource.Error(message = response.msg))
                Log.d("islam", "getPets: " + response.msg.toString())

            }
        } catch (e: Exception) {
            _myPetsFlow.emit(Resource.Error(message = e.message.toString()))
            Log.d("islam", "getPets: " + e.message.toString())
        }
    }

    fun getClinicInfo(clinicId: String) = viewModelScope.launch {
        try {
            _clinicInfoFlow.emit(Resource.Loading())
            val response = repository.getClinicById(PrefsHelper.getLanguage(), clinicId)
            if (response.isSuccessful) {
                if (response.body()!!.status) {
                    _clinicInfoFlow.emit(Resource.Success(response.body()!!))
                } else {
                    _clinicInfoFlow.emit(Resource.Error(message = response.body()!!.msg))
                }
            } else {
                _clinicInfoFlow.emit(Resource.Error(message = response.body()!!.msg))
            }

        } catch (ex: CancellationException) {
            Log.d("islam", "exception: ${ex.message.toString()}")
        } catch (e: Exception) {
            Log.d("islam", "exception: ${e.message.toString()}")
            _clinicInfoFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }

    fun getRequestTypes() = viewModelScope.launch {
        try {
            _requestTypesFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getRequestTypes())
            if (response.status) {
                _requestTypesFlow.emit(Resource.Success(response.data!!))
            } else {
                _requestTypesFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _requestTypesFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }

    fun addRequest(addRequestBody: AddRequestBody, imagesBody: ImagesBody) = viewModelScope.launch {
        try {
            _addRequestFlow.emit(Resource.Loading())
            val response = handleResponse(repository.addRequest(addRequestBody, imagesBody))
            if (response.status) {
                _addRequestFlow.emit(Resource.Success(response.data!!))
            } else {
                _addRequestFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _addRequestFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }

}