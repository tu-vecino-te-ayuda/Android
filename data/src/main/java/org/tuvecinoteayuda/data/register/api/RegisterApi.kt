package org.tuvecinoteayuda.data.register.api

import org.tuvecinoteayuda.data.commons.models.AuthResponse
import org.tuvecinoteayuda.data.register.models.RegisterUserRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {

    @POST("public/auth/register")
    suspend fun registerUser(@Body request: RegisterUserRequest): AuthResponse
}