package com.tawajood.snail.ui.main.fragments.about_us

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.pojo.About
import com.tawajood.snail.repository.Repository
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutViewModel
@Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _aboutFlow = MutableStateFlow<Resource<About>>(Resource.Idle())
    val aboutFlow = _aboutFlow.asStateFlow()

    init {
        getAbout()
    }

    private fun getAbout() = viewModelScope.launch {
        try {
            _aboutFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getAbout())
            if (response.status) {
                _aboutFlow.emit(Resource.Success(response.data!!))
            } else {
                _aboutFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _aboutFlow.emit(Resource.Error(message = e.message!!))
        }
    }

}