package org.tuvecinoteayuda.dashboard

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tuvecinoteayuda.core.ui.ScreenState
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.helprequests.repository.HelpRequestRepository

class DashboardViewModel(private val repository: HelpRequestRepository) : ViewModel() {

    // State
    private val _screenState = MutableLiveData(ScreenState.INITIAL)
    val screenState: LiveData<ScreenState>
        get() = _screenState
    val requests = liveData(Dispatchers.IO) {
        when (val requestList = repository.getPendingRequestList()) {
            is ResultWrapper.Success -> emit(requestList.value.data)
        }
    }

    fun start() {
        // Nothing to do for now
    }
}
