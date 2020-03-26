package org.tuvecinoteayuda.data.profile.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.tuvecinoteayuda.data.*
import org.tuvecinoteayuda.data.profile.api.ProfileApi
import org.tuvecinoteayuda.data.profile.models.ProfileResponse
import org.tuvecinoteayuda.data.profile.models.UpdateProfileRequest
import org.tuvecinoteayuda.data.profile.models.UpdateProfileResponse

class ProfileRepository private constructor(
    private val api: ProfileApi,
    private val dispatcher: CoroutineDispatcher
) : BaseRepository() {

    suspend fun getProfile(): ResultWrapper<ProfileResponse> {
        return safeApiCall(dispatcher) { api.getProfile() }
    }

    suspend fun updateProfile(request: UpdateProfileRequest): ResultWrapper<UpdateProfileResponse> {
        return safeApiCall(dispatcher) { api.updateProfile(request) }
    }

    suspend fun doLogout() {
        TokenProvider.token = null
    }

    companion object {
        fun newInstance(
            dispatcher: CoroutineDispatcher = Dispatchers.IO
        ): ProfileRepository {
            return ProfileRepository(
                ServiceFactory.create(CommonInterceptor.newInstance(TokenProvider)),
                dispatcher
            )
        }
    }
}
