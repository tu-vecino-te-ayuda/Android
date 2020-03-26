package org.tuvecinoteayuda.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.commons.models.AuthResponse
import org.tuvecinoteayuda.data.login.repository.LoginRepository
import org.tuvecinoteayuda.core.util.Event
import org.tuvecinoteayuda.core.ui.ScreenState
import org.tuvecinoteayuda.data.commons.models.UserTypeId

class LoginViewModel(
    private val repository: LoginRepository
) : ViewModel() {

    // State
    private val _screenState = MutableLiveData(ScreenState.INITIAL)
    val screenState: LiveData<ScreenState>
        get() = _screenState

    // Events
    private val _onLoginSuccessEvent = MutableLiveData<Event<UserTypeId>>()
    val onLoginSuccessEvent: LiveData<Event<UserTypeId>>
        get() = _onLoginSuccessEvent
    private val _onLoginFailedEvent = MutableLiveData<Event<String>>()
    val onLoginFailedEvent: LiveData<Event<String>>
        get() = _onLoginFailedEvent
    private val _onLoginFailedGenericEvent = MutableLiveData<Event<Unit>>()
    val onLoginFailedGenericEvent: LiveData<Event<Unit>>
        get() = _onLoginFailedGenericEvent


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
            // Validate input
            val currentUser = user.value?.trim()
            if (currentUser.isNullOrBlank()) {
                _userError.postValue(true)
                onInvalidData()
                return@launch
            }
            val currentPassword = password.value?.trim()
            if (currentPassword.isNullOrBlank()) {
                _passwordError.postValue(true)
                onInvalidData()
                return@launch
            }
            // Login request
            when(val loginResult = repository.doLogin(currentUser, currentPassword)){
                is ResultWrapper.Success -> onLoginSuccess(loginResult.value)
                is ResultWrapper.GenericError -> onLoginFailed(loginResult.error.toString())
                else  -> onLoginGenericFailed()
            }
        }
    }

    private fun onInvalidData() {
        _screenState.value = ScreenState.DATA_LOADED
    }

    private fun onLoginSuccess(authResponse: AuthResponse) {
        _screenState.value = ScreenState.DATA_LOADED
        _onLoginSuccessEvent.postValue(Event(authResponse.user.userTypeId))
    }

    private fun onLoginFailed(error: String) {
        _screenState.value = ScreenState.DATA_LOADED
        _onLoginFailedEvent.postValue(Event(error))
    }

    private fun onLoginGenericFailed() {
        _screenState.value = ScreenState.DATA_LOADED
        _onLoginFailedGenericEvent.postValue(
            Event(
                Unit
            )
        )
    }

}
