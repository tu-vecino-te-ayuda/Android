package org.tuvecinoteayuda.data.register.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.tuvecinoteayuda.data.BaseRepository
import org.tuvecinoteayuda.data.CommonInterceptor
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.ServiceFactory
import org.tuvecinoteayuda.data.commons.models.ActivityAreaTypeId
import org.tuvecinoteayuda.data.commons.models.AuthResponse
import org.tuvecinoteayuda.data.commons.models.NearByAreaTypeId
import org.tuvecinoteayuda.data.commons.models.UserTypeId
import org.tuvecinoteayuda.data.register.api.RegisterApi
import org.tuvecinoteayuda.data.register.models.RegisterUserRequest

class RegisterRepository private constructor(
    private val api: RegisterApi,
    private val dispatcher: CoroutineDispatcher
) : BaseRepository() {

    fun getNearByAreaType(): List<NearByAreaTypeId> {
        return listOf(
            NearByAreaTypeId.BUILDING,
            NearByAreaTypeId.NEIGHBORHOOD,
            NearByAreaTypeId.CITY,
            NearByAreaTypeId.OUTSIDE
        )
    }

    suspend fun registerUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        passwordConfirmation: String,
        address: String,
        city: String,
        state: String,
        zipCode: String,
        corporateName: String? = null,
        cif: String? = null,
        @UserTypeId.Companion.UserType userTypeId: Int,
        @NearByAreaTypeId.Companion.NearByAreaType nearbyAreasId: Int? = null,
        @ActivityAreaTypeId.Companion.ActivityAreaType activityAreaType: Int? = null
    ): ResultWrapper<AuthResponse> {

        val request = RegisterUserRequest(
            name, email, phone, password, passwordConfirmation, address,
            city, state, zipCode, corporateName, cif, userTypeId, nearbyAreasId, activityAreaType
        )

        return safeApiCall(dispatcher) { api.registerUser(request) }
    }

    companion object {
        fun newInstance(dispatcher: CoroutineDispatcher = Dispatchers.IO): RegisterRepository {
            return RegisterRepository(
                ServiceFactory.create(CommonInterceptor.newInstance()),
                dispatcher
            )
        }
    }
}
