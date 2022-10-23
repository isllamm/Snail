package com.tawajood.snail.ui.main.fragments.consultants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.pojo.ConsultantResponse
import com.tawajood.snail.pojo.ConsultantsResponse
import com.tawajood.snail.repository.Repository
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyConsultantsViewModel @Inject
constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _consultantsFlow =
        MutableStateFlow<Resource<ConsultantsResponse>>(Resource.Idle())
    val consultantsFlow = _consultantsFlow.asStateFlow()

    private val _consultantFlow =
        MutableStateFlow<Resource<ConsultantResponse>>(Resource.Idle())
    val consultantFlow = _consultantFlow.asStateFlow()

    private val _cancelConsultantFlow =
        MutableStateFlow<Resource<Any>>(Resource.Idle())
    val cancelConsultantFlow = _cancelConsultantFlow.asStateFlow()

    init {
        getConsultants()
    }

    private fun getConsultants() = viewModelScope.launch {
        try {
            _consultantsFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getConsultants())
            if (response.status) {
                _consultantsFlow.emit(Resource.Success(response.data!!))
            } else {
                _consultantsFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _consultantsFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }

    fun getConsultantById(requestId: String) = viewModelScope.launch {
        try {
            _consultantFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getConsultantById(requestId))
            if (response.status) {
                _consultantFlow.emit(Resource.Success(response.data!!))
            } else {
                _consultantFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _consultantFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }

    fun cancelConsultantById(requestId: String) = viewModelScope.launch {
        try {
            _cancelConsultantFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getConsultantById(requestId))
            if (response.status) {
                _cancelConsultantFlow.emit(Resource.Success(response.data!!))
            } else {
                _cancelConsultantFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _cancelConsultantFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }
}
