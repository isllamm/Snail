package com.tawajood.snail.ui.main.fragments.notifications

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.snail.pojo.Notification
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
class NotificationsViewModel
@Inject
constructor(
    private val repository: Repository
): ViewModel(){

    private val _notificationsFlow = MutableStateFlow<Resource<MutableList<Notification>>>(Resource.Idle())
    val notificationsFlow = _notificationsFlow.asStateFlow()

    init {
        getNotifications()
    }

    private fun getNotifications() = viewModelScope.launch {
        try {
            _notificationsFlow.emit(Resource.Loading())
            val response = handleResponse(repository.getNotifications())
            if (response.status) {
                _notificationsFlow.emit(Resource.Success(response.data!!.notifications.asReversed()))
            } else {
                _notificationsFlow.emit(Resource.Error(message = response.msg))
            }
        }catch (ex: CancellationException) {
            Log.d("islam", "exception: ${ex.message.toString()}")
        } catch (e: Exception) {
            _notificationsFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }
}