package org.tuvecinoteayuda.register

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import org.tuvecinoteayuda.data.regions.repository.RegionRepository
import org.tuvecinoteayuda.utils.ScreenState

class RegisterViewModel(
    private val repository: RegionRepository
) : ViewModel() {

    // State
    private val _screenState = MutableLiveData(ScreenState.INITIAL)
    val screenState: LiveData<ScreenState>
        get() = _screenState

    // Data - form
    val name = MutableLiveData<String>()
    private val _nameError = MutableLiveData(false)
    val nameError: LiveData<Boolean>
        get() = _nameError
    val email = MutableLiveData<String>()
    private val _emailError = MutableLiveData(false)
    val emailError: LiveData<Boolean>
        get() = _emailError
    val phone = MutableLiveData<String>()
    private val _phoneError = MutableLiveData(false)
    val phoneError: LiveData<Boolean>
        get() = _phoneError
    val password = MutableLiveData<String>()
    private val _passwordError = MutableLiveData(false)
    val passwordError: LiveData<Boolean>
        get() = _passwordError
    val repeatedPassword = MutableLiveData<String>()
    private val _repeatedPasswordError = MutableLiveData(false)
    val repeatedPasswordError: LiveData<Boolean>
        get() = _repeatedPasswordError
    val region = MutableLiveData<String>()
    private val _regionError = MutableLiveData(false)
    val regionError: LiveData<Boolean>
        get() = _regionError
    val city = MutableLiveData<String>()
    private val _cityError = MutableLiveData(false)
    val cityError: LiveData<Boolean>
        get() = _cityError
    val address = MutableLiveData<String>()
    private val _addressError = MutableLiveData(false)
    val addressError: LiveData<Boolean>
        get() = _addressError
    val postalCode = MutableLiveData<String>()
    private val _postalCodeError = MutableLiveData(false)
    val postalCodeError: LiveData<Boolean>
        get() = _postalCodeError

    // Data
    val regions = liveData(Dispatchers.IO) {
        emit(repository.getRegions())
    }
    val cities = Transformations.map(region) { regionName ->
        repository.getCitiesFromRegion(regionName)
    }

    fun start(registerType: RegisterType) {
        if (_screenState.value != ScreenState.INITIAL) return
        // TODO adjust screen based on register type
    }
}
