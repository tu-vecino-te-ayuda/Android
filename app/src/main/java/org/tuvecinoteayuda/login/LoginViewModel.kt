package org.tuvecinoteayuda.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.commons.models.AuthResponse
import org.tuvecinoteayuda.data.login.repository.LoginRepository
import org.tuvecinoteayuda.utils.Event
import org.tuvecinoteayuda.utils.ScreenState

class LoginViewModel(
    private val repository: LoginRepository
) : ViewModel() {

    // State
    private val _screenState = MutableLiveData(ScreenState.INITIAL)
    val screenState: LiveData<ScreenState>
        get() = _screenState

    // Events
    private val _treatmentUpdatedEvent = MutableLiveData<Event<Unit>>()
    val treatmentUpdatedEvent: LiveData<Event<Unit>>
        get() = _treatmentUpdatedEvent

    // Data
    val user = MutableLiveData<String>()
    private val _userError = MutableLiveData(false)
    val userError: LiveData<Boolean>
        get() = _userError

    val password = MutableLiveData<String>()
    private val _passwordError = MutableLiveData(false)
    val passwordError: LiveData<Boolean>
        get() = _passwordError

    fun start() {
        if (_screenState.value != ScreenState.INITIAL) return
        // if we need to load some data do it here
    }

    fun login() {
        _screenState.value = ScreenState.LOADING_DATA

        viewModelScope.launch {
            val currentUser = user.value?.trim()
            if (currentUser.isNullOrBlank()) {
                _userError.postValue(true)
                return@launch
            }
            val currentPassword = password.value?.trim()
            if (currentPassword.isNullOrBlank()) {
                _passwordError.postValue(true)
                return@launch
            }

            val loginResult = repository.doLogin(currentUser, currentPassword)
            when(loginResult){
                is ResultWrapper.Success -> onLoginSuccess(loginResult.value)
                else  -> onLoginFailed()
            }
        }
    }

    private fun onLoginSuccess(authResponse: AuthResponse) {
        _screenState.value = ScreenState.DATA_LOADED
    }

    private fun onLoginFailed() {
        _screenState.value = ScreenState.ERROR
    }

    fun onWantToHelp(){

    }

    fun onNeedHelp(){

    }
}
