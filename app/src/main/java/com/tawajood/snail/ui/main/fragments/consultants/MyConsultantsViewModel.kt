package com.tawajood.snail.ui.main.fragments.consultants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        getConsultants()
    }

    private fun getConsultants() = viewModelScope.launch {
        try {
            _consultantsFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getConsultants())
            if (response.status){
                _consultantsFlow.emit(Resource.Success(response.data!!))
            }else{
                _consultantsFlow.emit(Resource.Error(message = response.msg))
            }
        }catch (e: Exception){
            _consultantsFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }
}
