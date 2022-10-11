package com.tawajood.snail.ui.main.fragments.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.pojo.ClinicsResponse
import com.tawajood.snail.pojo.SliderResponse
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
class HomeViewModel @Inject
constructor(
    private val repository: Repository,
) : ViewModel() {
    private val _sliderFlow = MutableStateFlow<Resource<SliderResponse>>(Resource.Idle())
    val sliderFlow = _sliderFlow.asStateFlow()

    private val _clinicFlow = MutableStateFlow<Resource<ClinicsResponse>>(Resource.Idle())
    val clinicFlow = _clinicFlow.asStateFlow()

    init {
        getSlider()
        getClinics()
    }

    private fun getClinics() = viewModelScope.launch {
        try {
            Log.d("islam", "getClinics: here")
            _clinicFlow.emit(Resource.Loading())
            Log.d("islam", "getClinics: here1")
            val response = repository.getClinics()
            Log.d("islam", "getClinics: " + response.code().toString())
            if (response.isSuccessful) {
                Log.d("islam", "getClinics: " + "sss")
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
        } catch (e: Exception) {
            Log.d("islam", "exception: ${e.message.toString()}")
            _sliderFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }

    private fun getSlider() = viewModelScope.launch {
        try {
            _sliderFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getSlider())
            if (response.status) {
                _sliderFlow.emit(Resource.Success(response.data!!))
            } else {
                _sliderFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (ex: CancellationException) {
            Log.d("islam", "exception: ${ex.message.toString()}")
        } catch (e: Exception) {
            _sliderFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }
}