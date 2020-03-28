package org.tuvecinoteayuda.profile

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tuvecinoteayuda.core.ui.ScreenState
import org.tuvecinoteayuda.core.util.Event
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.commons.models.NearByAreaTypeId.Companion.BUILDING
import org.tuvecinoteayuda.data.commons.models.User
import org.tuvecinoteayuda.data.commons.models.UserTypeId
import org.tuvecinoteayuda.data.profile.models.ProfileResponse
import org.tuvecinoteayuda.data.profile.repository.ProfileRepository
import org.tuvecinoteayuda.data.regions.repository.RegionRepository
import org.tuvecinoteayuda.data.register.repository.RegisterRepository
import org.tuvecinoteayuda.register.RegisterType
import org.tuvecinoteayuda.register.RegisterViewModel.Companion.PHONE_LENGTH
import org.tuvecinoteayuda.register.RegisterViewModel.Companion.POSTAL_CODE_LENGTH

class ProfileViewModel(
    private val regionRepository: RegionRepository,
    private val registerRepository: RegisterRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    // Screen State
    private val _screenState = MutableLiveData(ScreenState.INITIAL)
    val screenState: LiveData<ScreenState>
        get() = _screenState
    private val _edit = MutableLiveData(false)
    val edit: LiveData<Boolean>
        get() = _edit

    // Events
    private val _onProfileUpdateSuccessEvent = MutableLiveData<Event<Unit>>()
    val onProfileUpdateSuccessEvent: LiveData<Event<Unit>>
        get() = _onProfileUpdateSuccessEvent
    private val _onProfileUpdateFailedEvent = MutableLiveData<Event<Unit>>()
    val onProfileUpdateFailedEvent: LiveData<Event<Unit>>
        get() = _onProfileUpdateFailedEvent

    // Form fields
    private var user: User? = null
    private var registerType = MutableLiveData(RegisterType.Voluntary)

    val name = MutableLiveData(" ")
    private val _nameError = MutableLiveData(false)
    val nameError: LiveData<Boolean>
        get() = _nameError

    val email = MutableLiveData(" ")
    private val _emailError = MutableLiveData(false)
    val emailError: LiveData<Boolean>
        get() = _emailError

    val phone = MutableLiveData(" ")
    private val _phoneError = MutableLiveData(false)
    val phoneError: LiveData<Boolean>
        get() = _phoneError

    val area = MutableLiveData(BUILDING)
    private val _areaError = MutableLiveData(false)
    val areaError: LiveData<Boolean>
        get() = _areaError

    val address = MutableLiveData(" ")
    private val _addressError = MutableLiveData(false)
    val addressError: LiveData<Boolean>
        get() = _addressError

    val region = MutableLiveData(" ")
    private val _regionError = MutableLiveData(false)
    val regionError: LiveData<Boolean>
        get() = _regionError

    val city = MutableLiveData(" ")
    private val _cityError = MutableLiveData(false)
    val cityError: LiveData<Boolean>
        get() = _cityError

    val postalCode = MutableLiveData(" ")
    private val _postalCodeError = MutableLiveData(false)
    val postalCodeError: LiveData<Boolean>
        get() = _postalCodeError

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

    fun start() {
        if (_screenState.value != ScreenState.INITIAL) return
        getProfile()
    }

    private fun getProfile() {
        _screenState.value = ScreenState.LOADING_DATA
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = profileRepository.getProfile()) {
                is ResultWrapper.Success -> onProfileLoaded(response.value)
                else -> _screenState.value = ScreenState.ERROR
            }
        }
    }

    private fun onProfileLoaded(profileResponse: ProfileResponse) {
        val user = profileResponse.user
        this.user = user
        registerType.postValue(
            when (user.userTypeId.id) {
                UserTypeId.VOLUNTARIO_ID -> RegisterType.Voluntary
                else -> RegisterType.Requester
            }
        )
        name.postValue(user.name)
        email.postValue(user.email)
        phone.postValue(user.phone)
        area.postValue(user.nearbyAreasId)
        address.postValue(user.address)
        region.postValue(regionRepository.getRegionById(user.state)?.name ?: "")
        city.postValue(regionRepository.getCityById(user.state, user.city)?.name ?: "")
        postalCode.postValue(user.zipCode)
        _screenState.postValue(ScreenState.DATA_LOADED)
    }

    fun editUpdate() {
        val editing = edit.value == true
        when {
            editing -> update()
            !editing && user == null -> return
            else -> _edit.value = editing.not()
        }
    }

    private fun update() {
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
            // Area
            val currentArea = area.value
            if (currentArea == null) {
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
            // Call update profile endpoint
            val currentUser = user ?: return@launch
            currentUser.apply {
                name = currentName
                email = currentEmail
                phone = currentPhone
                nearbyAreasId = currentArea
                address = currentAddress
                state = currentRegion.id
                city = currentCity.id
                zipCode = currentPostalCode
            }
            when (profileRepository.updateProfile(currentUser)) {
                is ResultWrapper.Success -> onProfileUpdated()
                else -> onRegisterFailed()
            }
        }
    }

    private fun onInvalidData() {
        _screenState.postValue(ScreenState.DATA_LOADED)
    }

    private fun onProfileUpdated() {
        _screenState.postValue(ScreenState.DATA_LOADED)
        _edit.postValue(false)
        _onProfileUpdateSuccessEvent.postValue(Event(Unit))
    }

    private fun onRegisterFailed() {
        _screenState.postValue(ScreenState.DATA_LOADED)
        _onProfileUpdateFailedEvent.postValue(Event(Unit))
    }
}
