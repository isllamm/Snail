package com.tawajood.snail.ui.main.fragments.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.repository.Repository
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {

    private val _reviewFlow = MutableSharedFlow<Resource<Any>>()
    val reviewFlow = _reviewFlow.asSharedFlow()

    fun review(clinicId: String, rating: String, comment: String) = viewModelScope.launch {
        try {
            _reviewFlow.emit(Resource.Loading())
            val response = handleResponse(repository.review(clinicId, rating, comment))
            if (response.status) {
                _reviewFlow.emit(Resource.Success(response.data!!))
            } else {
                _reviewFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _reviewFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }
}