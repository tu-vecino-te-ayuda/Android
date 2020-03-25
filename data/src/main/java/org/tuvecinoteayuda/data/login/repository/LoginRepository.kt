package org.tuvecinoteayuda.data.login.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.tuvecinoteayuda.data.*
import org.tuvecinoteayuda.data.commons.models.AuthResponse
import org.tuvecinoteayuda.data.login.api.LoginApi
import org.tuvecinoteayuda.data.login.models.LoginRequest

class LoginRepository private constructor(
    private val api: LoginApi,
    private val dispatcher: CoroutineDispatcher,
    private val tokenProvider: TokenProvider
) : BaseRepository() {

    suspend fun doLogin(user: String, password: String): ResultWrapper<AuthResponse> {
        val login = safeApiCall(dispatcher) { api.login(LoginRequest(user, password)) }

        when (login) {
            is ResultWrapper.Success -> tokenProvider.token = login.value.token
        }

        return login
    }

    companion object {
        fun newInstance(dispatcher: CoroutineDispatcher = Dispatchers.IO): LoginRepository {
            return LoginRepository(
                ServiceFactory.create(CommonInterceptor.newInstance(TokenProvider)),
                dispatcher,
                TokenProvider
            )
        }
    }
}
