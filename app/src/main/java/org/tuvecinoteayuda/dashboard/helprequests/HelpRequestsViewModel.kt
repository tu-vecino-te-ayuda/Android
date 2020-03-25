package org.tuvecinoteayuda.dashboard.helprequests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.tuvecinoteayuda.data.regions.models.City
import org.tuvecinoteayuda.data.regions.repository.RegionRepository

class HelpRequestsViewModel(private val repository: RegionRepository) : ViewModel() {

    private val _city: MutableLiveData<City> = MutableLiveData()
    val city: LiveData<City>
        get() = _city

    //TODO Move to background
    fun findCityById(stateId: String, cityId: String) {
        val city = repository.getCityFromId(stateId, cityId)
        city?.let {
            this._city.postValue(it)
        }
    }
}
