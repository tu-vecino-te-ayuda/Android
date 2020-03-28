package org.tuvecinoteayuda.request_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tuvecinoteayuda.core.ui.ScreenState
import org.tuvecinoteayuda.core.util.Event
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.helprequests.models.HelpRequest
import org.tuvecinoteayuda.data.helprequests.repository.HelpRequestRepository
import org.tuvecinoteayuda.data.regions.models.City
import org.tuvecinoteayuda.data.regions.models.Region
import org.tuvecinoteayuda.data.regions.repository.RegionRepository

class RequestDetailViewModel(
    private val requestRepository: HelpRequestRepository,
    private val regionRepository: RegionRepository
) : ViewModel() {

    // Screen State
    private val _screenState = MutableLiveData(ScreenState.INITIAL)
    val screenState: LiveData<ScreenState>
        get() = _screenState

    //Variables
    private val _helpRequest = MutableLiveData<HelpRequest>()
    val helpRequest: LiveData<HelpRequest>
        get() = _helpRequest
    private val _state: MutableLiveData<Region> = MutableLiveData()
    val state: LiveData<Region>
        get() = _state
    private val _city: MutableLiveData<City> = MutableLiveData()
    val city: LiveData<City>
        get() = _city

    //Events
    private val _onAcceptRequestSuccessEvent = MutableLiveData<Event<Unit>>()
    val onAcceptRequestSuccessEvent: LiveData<Event<Unit>>
        get() = _onAcceptRequestSuccessEvent
    private val _onAcceptRequestErrorEvent = MutableLiveData<Event<String>>()
    val onAcceptRequestErrorEvent: LiveData<Event<String>>
        get() = _onAcceptRequestErrorEvent

    private var item: HelpRequest? = null
    fun start(requestId: String) {
        val request = requestRepository.findRequestById(requestId)

        request?.let {
            item = it
            _helpRequest.postValue(it)
            _state.postValue(regionRepository.getRegionById(it.user.state))
            _city.postValue(regionRepository.getCityById(it.user.state, it.user.city))
        } ?: error("Invalid args")
    }

    fun acceptRequest() {
        item?.also {
            viewModelScope.launch(Dispatchers.IO) {
                when (val result = requestRepository.acceptHelpRequest(it.id)) {
                    is ResultWrapper.Success -> {
                        _onAcceptRequestSuccessEvent.postValue(Event(Unit))
                    }
                    is ResultWrapper.GenericError ->{
                        _onAcceptRequestErrorEvent.postValue(Event(result.error!!.message))
                    }
                    else -> {}
                }
            }
        }

    }
}