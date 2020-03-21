package org.tuvecinoteayuda.data.register

import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.TestCase
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.commons.REGISTER_RESPONSE_OK
import org.tuvecinoteayuda.data.commons.models.AuthResponse
import org.tuvecinoteayuda.data.register.repository.RegisterRepository

class RegisterRepositoryTest {

    private val repository = mockk<RegisterRepository>()

    @Test
    fun `register is success`() {
        runBlockingTest {
            val registerResponse =
                Gson().fromJson<AuthResponse>(REGISTER_RESPONSE_OK, AuthResponse::class.java)

            coEvery {
                repository.registerUser(
                    any(), any(), any(), any(), any(), any(), any(), any(), any(),
                    any(), any(), any(), any()
                )
            } returns ResultWrapper.Success(registerResponse)

            val result = repository.registerUser(
                "", "", "", "", "",
                "", "", "", "", "", 1, null, null
            )
            assertEquals(ResultWrapper.Success(registerResponse), result)
        }
    }
}