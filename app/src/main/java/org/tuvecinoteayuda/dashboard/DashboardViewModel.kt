package org.tuvecinoteayuda.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
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

    private val _onAllRequestEmpty = MutableLiveData<Event<Unit>>()
    val onAllRequestEmpty: LiveData<Event<Unit>>
        get() = _onAllRequestEmpty

    private val _onMyRequestEmpty = MutableLiveData<Event<Unit>>()
    val onMyRequestEmpty: LiveData<Event<Unit>>
        get() = _onMyRequestEmpty

    //Data
    private val _request = MutableLiveData<List<HelpRequest>>()
    val allRequest: LiveData<List<HelpRequest>>
        get() = _request

    private val _requestError = MutableLiveData<String>()
    val requestError: LiveData<String>
        get() = _requestError

    private var userType: DashboardType = DashboardType.VOLUNTARY
    private var requestType: RequestType = RequestType.PENDING

    fun start(userType: DashboardType) {
        this.userType = userType
        when (userType) {
            DashboardType.REQUESTER -> {
                _onRequestedLoadedEvent.postValue(Event(Unit))
                getMyRequest()
            }
            DashboardType.VOLUNTARY -> {
                _onVoluntaryLoadedEvent.postValue(Event(Unit))
                getPendingRequest()
            }
        }
    }

    //TODO Join this two calls
    fun getMyRequest() {
        requestType = RequestType.MINE
        _screenState.postValue(ScreenState.LOADING_DATA)
        viewModelScope.launch {
            when (val pendingList = helpRequestRepository.getMyHelpRequests()) {
                is ResultWrapper.Success -> onDataLoaded(pendingList.value.data)
                is ResultWrapper.GenericError -> pendingList.error?.message?.let { showError(it) }
                else -> showError("")
            }
        }
    }

    //TODO Join this two calls
    fun getPendingRequest() {
        requestType = RequestType.PENDING
        _screenState.postValue(ScreenState.LOADING_DATA)
        viewModelScope.launch {
            when (val requestList = helpRequestRepository.getPendingHelpRequests()) {
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

        if (data.isEmpty()) {
            when (requestType) {
                RequestType.MINE -> {
                    _onMyRequestEmpty.postValue(Event(Unit))
                }
                RequestType.PENDING -> {
                    _onAllRequestEmpty.postValue(Event(Unit))
                }
            }
        }
        _showButton.postValue(this.userType == DashboardType.REQUESTER)
        _request.postValue(data)
    }

    private fun showError(error: String) {
        _screenState.postValue(ScreenState.ERROR)
        _requestError.postValue(error)
    }
}
