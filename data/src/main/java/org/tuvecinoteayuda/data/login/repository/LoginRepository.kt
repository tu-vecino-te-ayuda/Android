package org.tuvecinoteayuda.data.login.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.tuvecinoteayuda.data.BaseRepository
import org.tuvecinoteayuda.data.CommonInterceptor
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.ServiceFactory
import org.tuvecinoteayuda.data.commons.models.AuthResponse
import org.tuvecinoteayuda.data.login.api.LoginApi
import org.tuvecinoteayuda.data.login.models.LoginRequest

class LoginRepository private constructor(
    private val api: LoginApi,
    private val dispatcher: CoroutineDispatcher
) : BaseRepository() {

    suspend fun doLogin(user: String, password: String): ResultWrapper<AuthResponse> {
        return safeApiCall(dispatcher) { api.login(LoginRequest(user, password)) }
    }

    companion object {
        fun newInstance(dispatcher: CoroutineDispatcher = Dispatchers.IO): LoginRepository {
            return LoginRepository(
                ServiceFactory.create(CommonInterceptor.newInstance()),
                dispatcher
            )
        }
    }
}
