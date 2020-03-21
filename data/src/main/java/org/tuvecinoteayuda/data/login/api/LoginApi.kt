package org.tuvecinoteayuda.data.login.api

import org.tuvecinoteayuda.data.login.models.LoginRequest
import org.tuvecinoteayuda.data.commons.models.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): AuthResponse
}