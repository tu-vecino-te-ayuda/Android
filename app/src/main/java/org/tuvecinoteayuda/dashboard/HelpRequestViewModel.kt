package org.tuvecinoteayuda.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.tuvecinoteayuda.data.regions.models.City
import org.tuvecinoteayuda.data.regions.repository.RegionRepository

class HelpRequestViewModel(private val repository: RegionRepository) : ViewModel() {

    val city = MutableLiveData<City>()
    private val _city: MutableLiveData<City>
        get() = city

    //TODO Move to background
    fun findCityById(stateId: String, cityId: String) {
        val city = repository.getCityFromId(stateId, cityId)

        city?.let {
            this._city.postValue(it)
        }
    }
}