package com.tawajood.snail.ui.main.fragments.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.pojo.ClinicsResponse
import com.tawajood.snail.repository.Repository
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val repository: Repository,
) : ViewModel() {
    private val _clinicResultFlow = MutableStateFlow<Resource<ClinicsResponse>>(Resource.Idle())
    val clinicResultFlow = _clinicResultFlow.asStateFlow()


     fun search(searchName: String) = viewModelScope.launch {
        try {
            _clinicResultFlow.emit(Resource.Loading())
            val response = repository.search(searchName)
            if (response.isSuccessful) {
                if (response.body()!!.status) {
                    _clinicResultFlow.emit(Resource.Success(response.body()!!))
                } else {
                    _clinicResultFlow.emit(Resource.Error(message = response.body()!!.msg))
                }
            } else {
                _clinicResultFlow.emit(Resource.Error(message = response.body()!!.msg))

            }

        } catch (e: Exception) {
            _clinicResultFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }

}