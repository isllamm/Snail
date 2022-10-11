package com.tawajood.snail.ui.main.fragments.terms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.pojo.Terms
import com.tawajood.snail.repository.Repository
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TermsViewModel
@Inject
constructor(
    private val repository: Repository
): ViewModel(){

    private val _termsFlow = MutableStateFlow<Resource<Terms>>(Resource.Idle())
    val termsFlow = _termsFlow.asStateFlow()

    init {
        getTerms()
    }

    private fun getTerms() = viewModelScope.launch {
        try {
            _termsFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getTerms())
            if (response.status) {
                _termsFlow.emit(Resource.Success(response.data!!))
            } else {
                _termsFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _termsFlow.emit(Resource.Error(message = e.message!!))
        }
    }
}