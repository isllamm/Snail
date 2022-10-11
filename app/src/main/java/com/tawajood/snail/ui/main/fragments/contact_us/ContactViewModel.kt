package com.tawajood.snail.ui.main.fragments.contact_us

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.pojo.Contact
import com.tawajood.snail.repository.Repository
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel
@Inject
constructor(
    private val repository: Repository
): ViewModel(){

    private val _contactFlow = MutableStateFlow<Resource<Contact>>(Resource.Idle())
    val contactFlow = _contactFlow.asStateFlow()




    init {
        getContact()
    }

    private fun getContact() = viewModelScope.launch {
        try {
            _contactFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getContact())
            if (response.status) {
                _contactFlow.emit(Resource.Success(response.data!!.contact))
            } else {
                _contactFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _contactFlow.emit(Resource.Error(message = e.message!!))
        }
    }



}