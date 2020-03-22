package org.tuvecinoteayuda.regions

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tuvecinoteayuda.data.regions.repository.RegionRepository
import org.tuvecinoteayuda.register.RegisterType
import org.tuvecinoteayuda.utils.ScreenState

class RegionsViewModel(
    private val repository: RegionRepository
) : ViewModel() {

    // State
    private val _screenState = MutableLiveData(ScreenState.INITIAL)
    val screenState: LiveData<ScreenState>
        get() = _screenState

    // Data
    val regions = liveData(Dispatchers.IO) {
        emit(repository.getRegions())
    }

    fun start() {
        if (_screenState.value != ScreenState.INITIAL) return
        // TODO adjust screen based on register type
    }
}
