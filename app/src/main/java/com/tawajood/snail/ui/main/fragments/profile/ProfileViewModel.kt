package com.tawajood.snail.ui.main.fragments.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.pojo.Profile
import com.tawajood.snail.repository.Repository
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {
    private val _profileFlow = MutableStateFlow<Resource<Profile>>(Resource.Idle())
    val profileFlow = _profileFlow.asStateFlow()

    init {
        getProfile()
    }

    private fun getProfile() = viewModelScope.launch {
        try {
            _profileFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getProfile())
            if (response.status) {
                _profileFlow.emit(Resource.Success(response.data!!.profile))
            } else {
                _profileFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _profileFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }
}