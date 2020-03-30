package org.tuvecinoteayuda.register

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tuvecinoteayuda.core.ui.ScreenState
import org.tuvecinoteayuda.core.util.Event
import org.tuvecinoteayuda.dashboard.DashboardType
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.commons.models.NearByAreaTypeId
import org.tuvecinoteayuda.data.commons.models.UserTypeId
import org.tuvecinoteayuda.data.regions.repository.RegionRepository
import org.tuvecinoteayuda.data.register.repository.RegisterRepository

class RegisterViewModel(
    private val regionRepository: RegionRepository,
    private val registerRepository: RegisterRepository
) : ViewModel() {

    // State
    private val _screenState = MutableLiveData(ScreenState.INITIAL)
    val screenState: LiveData<ScreenState>
        get() = _screenState

    // Events
    private val _onRegisterSuccessEvent = MutableLiveData<Event<DashboardType>>()
    val onRegisterSuccessEvent: LiveData<Event<DashboardType>>
        get() = _onRegisterSuccessEvent
    private val _onRegisterFailedEvent = MutableLiveData<Event<Unit>>()
    val onRegisterFailedEvent: LiveData<Event<Unit>>
        get() = _onRegisterFailedEvent

    // Form fields
    private var registerType = MutableLiveData<RegisterType>()

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

    val area = MutableLiveData<NearByAreaTypeId>()
    private val _areaError = MutableLiveData(false)
    val areaError: LiveData<Boolean>
        get() = _areaError

    val address = MutableLiveData<String>()
    private val _addressError = MutableLiveData(false)
    val addressError: LiveData<Boolean>
        get() = _addressError

    val region = MutableLiveData<String>()
    private val _regionError = MutableLiveData(false)
    val regionError: LiveData<Boolean>
        get() = _regionError

    val city = MutableLiveData<String>()
    private val _cityError = MutableLiveData(false)
    val cityError: LiveData<Boolean>
        get() = _cityError

    val postalCode = MutableLiveData<String>()
    private val _postalCodeError = MutableLiveData(false)
    val postalCodeError: LiveData<Boolean>
        get() = _postalCodeError

    val termsAndConditions = MutableLiveData<Boolean>()
    private val _termsAndConditionsError = MutableLiveData<Event<Unit>>()
    val termsAndConditionsError: LiveData<Event<Unit>>
        get() = _termsAndConditionsError

    // Spinners data
    val regions = liveData(Dispatchers.IO) {
        emit(regionRepository.getRegions())
    }
    val cities = Transformations.map(region) { regionName ->
        regionRepository.getCitiesByRegionName(regionName)
    }
    val areas = Transformations.map(registerType) { registerType ->
        when (registerType) {
            RegisterType.Voluntary -> registerRepository.getNearByAreaType()
            else -> listOf()
        }
    }

    fun start(registerType: RegisterType) {
        if (_screenState.value != ScreenState.INITIAL) return
        this.registerType.postValue(registerType)
    }

    fun register() {
        _screenState.value = ScreenState.LOADING_DATA
        viewModelScope.launch {
            // Name
            val currentName = name.value
            if (currentName.isNullOrBlank()) {
                _nameError.postValue(true)
                onInvalidData()
                return@launch
            }
            // Email
            val currentEmail = email.value
            if (currentEmail.isNullOrBlank()) {
                _emailError.postValue(true)
                onInvalidData()
                return@launch
            }
            // Phone
            val currentPhone = phone.value
            if (currentPhone.isNullOrBlank() || currentPhone.length != PHONE_LENGTH) {
                _phoneError.postValue(true)
                onInvalidData()
                return@launch
            }
            // Password
            val currentPassword = password.value
            if (currentPassword.isNullOrBlank()
                || currentPassword.length < MIN_PASSWORD_CHARACTERS
            ) {
                _passwordError.postValue(true)
                onInvalidData()
                return@launch
            }
            // Area
            val currentArea = area.value
            if (registerType.value == RegisterType.Voluntary && currentArea == null) {
                _areaError.postValue(true)
                onInvalidData()
                return@launch
            }
            // Address
            val currentAddress = address.value
            if (currentAddress.isNullOrBlank()) {
                _addressError.postValue(true)
                onInvalidData()
                return@launch
            }
            // Region
            val currentRegion =
                region.value?.let { name -> regionRepository.getRegionByName(name) }
            if (currentRegion == null) {
                _regionError.postValue(true)
                onInvalidData()
                return@launch
            }
            // City
            val currentCity =
                city.value?.let { name -> regionRepository.getCityByName(currentRegion.name, name) }
            if (currentCity == null) {
                _cityError.postValue(true)
                onInvalidData()
                return@launch
            }
            // Postal code
            val currentPostalCode = postalCode.value
            if (currentPostalCode.isNullOrBlank()
                || currentPostalCode.length != POSTAL_CODE_LENGTH
            ) {
                _postalCodeError.postValue(true)
                onInvalidData()
                return@launch
            }
            // Call register endpoint
            val response = registerRepository.registerUser(
                name = currentName,
                email = currentEmail,
                phone = currentPhone,
                password = currentPassword,
                passwordConfirmation = currentPassword,
                address = currentAddress,
                city = currentCity.id,
                state = currentRegion.id,
                zipCode = currentPostalCode,
                userTypeId = when (registerType.value) {
                    RegisterType.Voluntary -> UserTypeId.VOLUNTARIO_ID
                    RegisterType.Requester -> UserTypeId.SOLICITANTE_ID
                    else -> error("Invalid register type!")
                },
                nearbyAreasId = currentArea?.id
            )
            when (response) {
                is ResultWrapper.Success -> onRegisterSuccess()
                else -> onRegisterFailed()
            }
        }
    }

    private fun onInvalidData() {
        _screenState.value = ScreenState.DATA_LOADED
    }

    private fun onRegisterSuccess() {
        _screenState.value = ScreenState.DATA_LOADED

        _onRegisterSuccessEvent.postValue(
            Event(
                when (registerType.value) {
                    RegisterType.Voluntary -> DashboardType.VOLUNTARY
                    RegisterType.Requester -> DashboardType.REQUESTER
                    else -> error("Invalid register type!")
                }
            )
        )
    }

    private fun onRegisterFailed() {
        _screenState.value = ScreenState.DATA_LOADED
        _onRegisterFailedEvent.postValue(Event(Unit))
    }

    companion object {
        const val PHONE_LENGTH = 9
        const val POSTAL_CODE_LENGTH = 5
        const val MIN_PASSWORD_CHARACTERS = 8
    }
}
