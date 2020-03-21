package org.tuvecinoteayuda.data.profile.api

import org.tuvecinoteayuda.data.commons.models.User
import org.tuvecinoteayuda.data.profile.models.UpdateProfileResponse
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.PUT

interface UserApi {

    @GET("user/update")
    fun getProfile(): User

    @PUT("user/update")
    fun updateProfile(@Body request: User): UpdateProfileResponse
}