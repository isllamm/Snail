package com.tawajood.snail.ui.main.fragments.clinic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.pojo.ClinicsResponse
import com.tawajood.snail.repository.Repository
import com.tawajood.snail.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClinicViewModel @Inject
constructor(
    private val repository: Repository,
) : ViewModel() {
    private val _clinicInfoFlow = MutableStateFlow<Resource<ClinicsResponse>>(Resource.Idle())
    val clinicInfoFlow = _clinicInfoFlow.asStateFlow()

    init {
    }

    fun getClinicInfo(clinicId: String) = viewModelScope.launch {
        try {
            _clinicInfoFlow.emit(Resource.Loading())
            val response = repository.getClinicById(PrefsHelper.getLanguage(), clinicId)
            if (response.isSuccessful) {
                Log.d("islam", "getClinics: " + "sss")
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

}