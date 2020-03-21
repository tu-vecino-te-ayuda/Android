package org.tuvecinoteayuda.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
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
    private val _onLoginSuccessEvent = MutableLiveData<Event<Unit>>()
    val onLoginSuccessEvent: LiveData<Event<Unit>>
        get() = _onLoginSuccessEvent
    private val _onLoginFailedEvent = MutableLiveData<Event<Unit>>()
    val onLoginFailedEvent: LiveData<Event<Unit>>
        get() = _onLoginFailedEvent

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
            // Reset errors
            _userError.postValue(true)
            _passwordError.postValue(true)
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
                else  -> onLoginFailed()
            }
        }
    }

    private fun onInvalidData() {
        _screenState.value = ScreenState.DATA_LOADED
    }

    private fun onLoginSuccess(authResponse: AuthResponse) {
        _screenState.value = ScreenState.DATA_LOADED
        _onLoginSuccessEvent.postValue(Event(Unit))
    }

    private fun onLoginFailed() {
        _screenState.value = ScreenState.DATA_LOADED
        _onLoginFailedEvent.postValue(Event(Unit))
    }
}
