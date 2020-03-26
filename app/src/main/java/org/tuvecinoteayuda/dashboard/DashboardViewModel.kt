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

class DashboardViewModel(private val repository: HelpRequestRepository) : ViewModel() {

    // State
    private val _screenState = MutableLiveData(ScreenState.INITIAL)
    val screenState: LiveData<ScreenState>
        get() = _screenState

    private val _request = MutableLiveData<List<HelpRequest>>()
    val allRequest: LiveData<List<HelpRequest>>
        get() = _request

    private val _requestError = MutableLiveData<String>()
    val requesrError: LiveData<String>
        get() = _requestError


    fun start() {
        getAllRequest()
    }

    //TODO Join this two calls
    fun getMyRequest() {
        _screenState.postValue(ScreenState.LOADING_DATA)
        viewModelScope.launch {
            when (val pendingList = repository.getRequest()) {
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
            when (val requestList = repository.getPendingRequestList()) {
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
        _request.postValue(data)

    }

    private fun showError(error: String) {
        _screenState.postValue(ScreenState.ERROR)
        _requestError.postValue(error)
    }
}
