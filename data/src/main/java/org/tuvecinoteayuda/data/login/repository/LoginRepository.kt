package org.tuvecinoteayuda.data.login.repository

import org.tuvecinoteayuda.data.BaseFactory
import org.tuvecinoteayuda.data.CommonInterceptor
import org.tuvecinoteayuda.data.login.api.LoginApi
import org.tuvecinoteayuda.data.login.models.LoginRequest
import org.tuvecinoteayuda.data.commons.models.AuthResponse

class LoginRepository private constructor(private val api: LoginApi) {

    suspend fun doLogin(user: String, password: String): AuthResponse {
        return api.login(LoginRequest(user, password))
    }

    companion object {
        fun newInstance(): LoginRepository {
            return LoginRepository(BaseFactory.create<LoginApi>(CommonInterceptor.newInstance()))
        }
    }
}