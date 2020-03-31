package org.tuvecinoteayuda.requesthelp

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tuvecinoteayuda.core.ui.ScreenState
import org.tuvecinoteayuda.core.util.Event
import org.tuvecinoteayuda.core.util.resetErrors
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.helprequests.models.CreateHelpRequestRequest
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestType
import org.tuvecinoteayuda.data.helprequests.repository.HelpRequestRepository

class RequestHelpViewModel(
    private val helpRequestRepository: HelpRequestRepository
) : ViewModel() {

    // Screen State
    private val _screenState = MutableLiveData(ScreenState.INITIAL)
    val screenState: LiveData<ScreenState>
        get() = _screenState

    // Events
    private val _onHelpRequestCreatedEvent = MutableLiveData<Event<Unit>>()
    val onHelpRequestCreatedEvent: LiveData<Event<Unit>>
        get() = _onHelpRequestCreatedEvent
    private val _onHelpRequestFailedEvent = MutableLiveData<Event<Unit>>()
    val onHelpRequestFailedEvent: LiveData<Event<Unit>>
        get() = _onHelpRequestFailedEvent

    // Form fields
    val helpRequestType = MutableLiveData<HelpRequestType>()
    private val _helpRequestTypeError = MutableLiveData(false)
    val helpRequestTypeError: LiveData<Boolean>
        get() = _helpRequestTypeError

    val message = MutableLiveData<String>()
    private val _messageError = MutableLiveData(false)
    val messageError: LiveData<Boolean>
        get() = _messageError

    val termsAndConditions = MutableLiveData<Boolean>(false)
    private val _termsAndConditionsError = MutableLiveData<Boolean>()
    val termsAndConditionsError: LiveData<Boolean>
        get() = _termsAndConditionsError

    // Spinners data
    val helpRequestTypes = liveData(Dispatchers.IO) {
        _screenState.postValue(ScreenState.LOADING_DATA)
        when (val result = helpRequestRepository.getHelpRequestTypes()) {
            is ResultWrapper.Success -> {
                emit(result.value.data)
                _screenState.postValue(ScreenState.DATA_LOADED)
            }
        }
    }

    fun start() {
        if (_screenState.value != ScreenState.INITIAL) return
        // Nothing to do
    }

    fun createRequest() {
        _screenState.value = ScreenState.LOADING_DATA
        viewModelScope.launch {
            // Reset errors
            resetErrors(_helpRequestTypeError, _messageError, _termsAndConditionsError)
            // Help request type
            val currentHelpRequestType = helpRequestType.value
            if (currentHelpRequestType == null) {
                _helpRequestTypeError.postValue(true)
                onInvalidData()
                return@launch
            }
            // Message
            val currentMessage = message.value
            if (currentMessage.isNullOrBlank()) {
                _messageError.postValue(true)
                onInvalidData()
                return@launch
            }

            // Terms and conditions
            val accepted = termsAndConditions.value ?: false
            if (!accepted) {
                _termsAndConditionsError.postValue(true)
                onInvalidData()
                return@launch
            }

            // Call create request endpoint
            val request = CreateHelpRequestRequest(
                helpRequestTypeId = currentHelpRequestType.id,
                message = currentMessage
            )
            when (helpRequestRepository.createHelpRequest(request)) {
                is ResultWrapper.Success -> onHelpRequestCreated()
                else -> onHelpRequestFailed()
            }
        }
    }

    private fun onInvalidData() {
        _screenState.postValue(ScreenState.DATA_LOADED)
    }

    private fun onHelpRequestCreated() {
        _screenState.postValue(ScreenState.DATA_LOADED)
        _onHelpRequestCreatedEvent.postValue(Event(Unit))
    }

    private fun onHelpRequestFailed() {
        _screenState.postValue(ScreenState.DATA_LOADED)
        _onHelpRequestFailedEvent.postValue(Event(Unit))
    }
}
