package org.tuvecinoteayuda.data.register.repository

import org.tuvecinoteayuda.data.BaseFactory
import org.tuvecinoteayuda.data.CommonInterceptor
import org.tuvecinoteayuda.data.commons.models.AuthResponse
import org.tuvecinoteayuda.data.commons.models.UserTypeId
import org.tuvecinoteayuda.data.register.api.RegisterApi
import org.tuvecinoteayuda.data.register.models.RegisterUserRequest

class RegisterRepository private constructor(private val api: RegisterApi) {

    suspend fun registerUser(
        name: String, phone: String, password: String, passwordConfirmation: String,
        address: String, city: String, state: String, zipCode: String, corporateName: String?
        , cif: String?, @UserTypeId.Companion.UserType userTypeId: Int,
        @RegisterUserRequest.Companion.NearByAreaType nearbyAreasId: Int?,
        @RegisterUserRequest.Companion.ActivityAreaType activityAreaType: Int?
    ): AuthResponse {

        val request = RegisterUserRequest(
            name, phone, password, passwordConfirmation, address,
            city, state, zipCode, corporateName, cif, userTypeId, nearbyAreasId, activityAreaType
        )

        return api.registerUser(request)
    }

    companion object {
        fun newInstance(): RegisterRepository {
            return RegisterRepository(BaseFactory.create<RegisterApi>(CommonInterceptor.newInstance()))
        }
    }
}