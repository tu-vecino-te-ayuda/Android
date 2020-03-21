package org.tuvecinoteayuda.data.login

import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.commons.LOGIN_RESPONSE_OK
import org.tuvecinoteayuda.data.commons.models.AuthResponse
import org.tuvecinoteayuda.data.login.repository.LoginRepository

class LoginRepositoryTest {

    private val repository = mockk<LoginRepository>(relaxed = true)

    @Test
    fun `login is success`() {
        runBlockingTest {
            val loginResponse =
                Gson().fromJson<AuthResponse>(LOGIN_RESPONSE_OK, AuthResponse::class.java)

            coEvery {
                repository.doLogin(any(), any())
            } returns ResultWrapper.Success(loginResponse)

            val result =   repository.doLogin("", "")
            assertEquals(ResultWrapper.Success(loginResponse), result)
        }
    }
}