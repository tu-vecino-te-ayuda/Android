package org.tuvecinoteayuda.data.profile.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.tuvecinoteayuda.data.*
import org.tuvecinoteayuda.data.commons.models.User
import org.tuvecinoteayuda.data.profile.api.UserApi
import org.tuvecinoteayuda.data.profile.models.ProfileResponse
import org.tuvecinoteayuda.data.profile.models.UpdateProfileResponse

class ProfileRepository private constructor(
    private val api: UserApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRepository() {

    suspend fun getProfile(): ResultWrapper<ProfileResponse> {
        return safeApiCall(dispatcher) { api.getProfile() }
    }

    suspend fun updateProfile(user: User): ResultWrapper<UpdateProfileResponse> {
        return safeApiCall(dispatcher) { api.updateProfile(user) }
    }

    suspend fun doLogout() {
        TokenProvider.token = null
    }

    companion object {

        fun newInstance(dispatcher: CoroutineDispatcher = Dispatchers.IO): ProfileRepository {
            return ProfileRepository(
                ServiceFactory.create<UserApi>(CommonInterceptor.newInstance(TokenProvider)),
                dispatcher
            )
        }
    }
}