package org.tuvecinoteayuda.data.login.api

import org.tuvecinoteayuda.data.login.models.LoginRequest
import org.tuvecinoteayuda.data.login.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

}