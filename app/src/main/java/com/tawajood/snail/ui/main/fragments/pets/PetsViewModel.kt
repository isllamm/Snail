package com.tawajood.snail.ui.main.fragments.pets

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.pojo.*
import com.tawajood.snail.repository.Repository
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetsViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {

    private val _addAnimalFlow = MutableStateFlow<Resource<Any>>(Resource.Idle())
    val addAnimalFlow = _addAnimalFlow.asSharedFlow()

    private val _petTypesFlow = MutableStateFlow<Resource<PetTypeResponse>>(Resource.Idle())
    val petTypesFlow = _petTypesFlow.asSharedFlow()

    private val _vaccinationTypesFlow =
        MutableStateFlow<Resource<VaccinationTypesResponse>>(Resource.Idle())
    val vaccinationTypesFlow = _vaccinationTypesFlow.asSharedFlow()

    private val _addVaccinationFlow =
        MutableStateFlow<Resource<Any>>(Resource.Idle())
    val addVaccinationFlow = _addVaccinationFlow.asSharedFlow()

    private val _myPetsFlow = MutableStateFlow<Resource<PetResponse>>(Resource.Idle())
    val myPetsFlow = _myPetsFlow.asSharedFlow()

    private val _myPetFlow = MutableStateFlow<Resource<PetByIdResponse>>(Resource.Idle())
    val myPetFlow = _myPetFlow.asSharedFlow()

    fun getPetById(petId: String) = viewModelScope.launch {
        try {
            _myPetFlow.emit(Resource.Loading())
            val response = handleResponse(repository.petById(petId))
            if (response.status) {
                _myPetFlow.emit(Resource.Success(response.data!!))
            } else {
                _myPetFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _myPetFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }


    fun getPetTypes() = viewModelScope.launch {
        try {
            _petTypesFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getPetTypes())
            if (response.status) {
                _petTypesFlow.emit(Resource.Success(response.data!!))
            } else {
                _petTypesFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _petTypesFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }

    fun getVaccinationTypes() = viewModelScope.launch {
        try {
            _vaccinationTypesFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getVaccinationTypes())
            if (response.status) {
                _vaccinationTypesFlow.emit(Resource.Success(response.data!!))
            } else {
                _vaccinationTypesFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _vaccinationTypesFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }

    fun getPets() = viewModelScope.launch {
        try {
            _myPetsFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getPets())

            if (response.status) {
                _myPetsFlow.emit(Resource.Success(response.data!!))
            } else {
                _myPetsFlow.emit(Resource.Error(message = response.msg))
                Log.d("islam", "getPets: " + response.msg.toString())

            }
        } catch (e: Exception) {
            _myPetsFlow.emit(Resource.Error(message = e.message.toString()))
            Log.d("islam", "getPets: " + e.message.toString())
        }
    }

    fun addPet(petBody: PetBody) = viewModelScope.launch {
        try {
            _addAnimalFlow.emit(Resource.Loading())
            val response = handleResponse(repository.addPet(petBody))
            if (response.status) {
                _addAnimalFlow.emit(Resource.Success(response.data!!))
            } else {
                _addAnimalFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _addAnimalFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }

    fun addVaccination(petId: String, date: String, typeId: String) = viewModelScope.launch {
        try {
            _addVaccinationFlow.emit(Resource.Loading())
            val response = handleResponse(repository.addVaccination(petId, date, typeId))
            if (response.status) {
                _addVaccinationFlow.emit(Resource.Success(response.data!!))
            } else {
                _addVaccinationFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _addVaccinationFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }
}