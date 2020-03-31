package org.tuvecinoteayuda.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.tuvecinoteayuda.core.ui.ScreenState
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

    //Data
    private val _dashboardType = MutableLiveData<DashboardType>()
    val dashboardType: LiveData<DashboardType>
        get() = _dashboardType

    private val _currentTab = MutableLiveData(DashboardTab.MY_REQUESTS)
    val currentTab: LiveData<DashboardTab>
        get() = _currentTab

    private val _requests = MutableLiveData<List<HelpRequest>>()
    val requests: LiveData<List<HelpRequest>>
        get() = _requests

    private val _requestError = MutableLiveData<String>()
    val requestError: LiveData<String>
        get() = _requestError

    fun start(userType: DashboardType) {
        if (_screenState.value == ScreenState.INITIAL) {
            _dashboardType.value = userType
            _currentTab.value = DashboardTab.MY_REQUESTS
        }
        refreshCurrentTab()
    }

    private fun refreshCurrentTab() {
        when (_currentTab.value) {
            DashboardTab.MY_REQUESTS -> getMyRequest()
            else -> getPendingRequest()
        }
    }

    fun getMyRequest() {
        _screenState.postValue(ScreenState.LOADING_DATA)
        _currentTab.value = DashboardTab.MY_REQUESTS
        viewModelScope.launch {
            when (val response = helpRequestRepository.getMyHelpRequests()) {
                is ResultWrapper.Success -> onDataLoaded(response.value.data)
                is ResultWrapper.GenericError -> response.error?.message?.let { showError(it) }
                else -> showError("")
            }
        }
    }

    fun getPendingRequest() {
        _screenState.postValue(ScreenState.LOADING_DATA)
        _currentTab.value = DashboardTab.PENDING_REQUESTS
        viewModelScope.launch {
            when (val response = helpRequestRepository.getPendingHelpRequests()) {
                is ResultWrapper.Success -> onDataLoaded(response.value.data)
                is ResultWrapper.GenericError -> response.error?.message?.let { showError(it) }
                else -> showError("")
            }
        }
    }

    private fun onDataLoaded(data: List<HelpRequest>) {
        _requests.postValue(data)
        _screenState.postValue(ScreenState.DATA_LOADED)
    }

    private fun showError(error: String) {
        _requestError.postValue(error)
        _screenState.postValue(ScreenState.ERROR)
    }
}
