package org.tuvecinoteayuda.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.helprequests.repository.HelpRequestRepository
import org.tuvecinoteayuda.core.ui.ScreenState
import org.tuvecinoteayuda.data.helprequests.models.HelpRequest

class DashboardViewModel(private val repository: HelpRequestRepository) : ViewModel() {

    // State
    private val _screenState = MutableLiveData(ScreenState.INITIAL)
    val screenState: LiveData<ScreenState>
        get() = _screenState
    val requests = MutableLiveData<List<HelpRequest>>()
    private val _request: LiveData<List<HelpRequest>>
        get() = requests


    fun start() {
        // Nothing to do for now
        viewModelScope.launch {
           val requestList =  repository.getRequest()

            when(requestList){
                is ResultWrapper.Success ->{

                }
                else ->{}
            }
        }
    }
}
