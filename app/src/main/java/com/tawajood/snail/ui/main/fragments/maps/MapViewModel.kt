package com.tawajood.snail.ui.main.fragments.maps

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.pojo.ClinicsResponse
import com.tawajood.snail.pojo.SliderResponse
import com.tawajood.snail.repository.Repository
import com.tawajood.snail.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject
constructor(
    private val repository: Repository,
) : ViewModel() {


    private val _clinicFlow = MutableStateFlow<Resource<ClinicsResponse>>(Resource.Idle())
    val clinicFlow = _clinicFlow.asStateFlow()

    init {
        getClinics()
    }

    private fun getClinics() = viewModelScope.launch {
        try {
            _clinicFlow.emit(Resource.Loading())
            val response = repository.getClinics()
            if (response.isSuccessful) {
                if (response.body()!!.status) {
                    _clinicFlow.emit(Resource.Success(response.body()!!))
                } else {
                    _clinicFlow.emit(Resource.Error(message = response.body()!!.msg))
                }
            } else {
                _clinicFlow.emit(Resource.Error(message = response.body()!!.msg))
            }

        } catch (ex: CancellationException) {
            Log.d("islam", "exception: ${ex.message.toString()}")
        }
    }
}