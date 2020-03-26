package org.tuvecinoteayuda.data.profile.api

import org.tuvecinoteayuda.data.commons.models.User
import org.tuvecinoteayuda.data.profile.models.ProfileResponse
import org.tuvecinoteayuda.data.profile.models.UpdateProfileRequest
import org.tuvecinoteayuda.data.profile.models.UpdateProfileResponse
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.PUT

interface ProfileApi {

    @GET("user/profile")
    suspend fun getProfile(): ProfileResponse

    @PUT("user/update")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): UpdateProfileResponse
}