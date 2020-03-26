package org.tuvecinoteayuda.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.tuvecinoteayuda.DashboardType
import org.tuvecinoteayuda.core.ui.ScreenState
import org.tuvecinoteayuda.core.util.Event
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.helprequests.models.HelpRequest
import org.tuvecinoteayuda.data.helprequests.repository.HelpRequestRepository
import org.tuvecinoteayuda.data.profile.repository.ProfileRepository


class DashboardViewModel(
    private val helpRequestRepository: HelpRequestRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    // State
    private val _screenState = MutableLiveData(ScreenState.INITIAL)
    val screenState: LiveData<ScreenState>
        get() = _screenState

    private val _showButton = MutableLiveData<Boolean>()
    val showButton: LiveData<Boolean>
        get() = _showButton

    //Events
    private val _onRequestedLoadedEvent = MutableLiveData<Event<Unit>>()
    val onRequestedLoadedEvent: LiveData<Event<Unit>>
        get() = _onRequestedLoadedEvent

    private val _onVoluntaryLoadedEvent = MutableLiveData<Event<Unit>>()
    val onVoluntaryLoadedEvent: LiveData<Event<Unit>>
        get() = _onVoluntaryLoadedEvent

    //Data
    private val _request = MutableLiveData<List<HelpRequest>>()
    val allRequest: LiveData<List<HelpRequest>>
        get() = _request

    private val _requestError = MutableLiveData<String>()
    val requestError: LiveData<String>
        get() = _requestError

    private var userType: DashboardType = DashboardType.Voluntary

    fun start(userType: DashboardType) {
        this.userType = userType
        when (userType) {
            DashboardType.Requester -> {
                _onRequestedLoadedEvent.postValue(Event(Unit))
                getMyRequest()
            }
            DashboardType.Voluntary -> {
                _onVoluntaryLoadedEvent.postValue(Event(Unit))
                getAllRequest()
            }
        }
    }

    //TODO Join this two calls
    fun getMyRequest() {
        _screenState.postValue(ScreenState.LOADING_DATA)
        viewModelScope.launch {
            when (val pendingList = helpRequestRepository.getRequest()) {
                is ResultWrapper.Success -> onDataLoaded(pendingList.value.data)
                is ResultWrapper.GenericError -> pendingList.error?.message?.let { showError(it) }
                else -> showError("")
            }
        }
    }

    //TODO Join this two calls
    fun getAllRequest() {
        _screenState.postValue(ScreenState.LOADING_DATA)
        viewModelScope.launch {
            when (val requestList = helpRequestRepository.getPendingRequestList()) {
                is ResultWrapper.Success -> onDataLoaded(requestList.value.data)
                is ResultWrapper.GenericError -> requestList.error?.message?.let { showError(it) }
                else -> showError("")
            }
        }
    }

    private fun onDataLoaded(data: List<HelpRequest>) {
        _screenState.postValue(
            if (data.isEmpty()) {
                ScreenState.EMPTY_DATA
            } else {
                ScreenState.DATA_LOADED
            }
        )

        _showButton.postValue(this.userType == DashboardType.Requester)
        _request.postValue(data)
    }

    private fun showError(error: String) {
        _screenState.postValue(ScreenState.ERROR)
        _requestError.postValue(error)
    }

    fun stop() {
        viewModelScope.launch {
            profileRepository.doLogout()
        }
    }
}
