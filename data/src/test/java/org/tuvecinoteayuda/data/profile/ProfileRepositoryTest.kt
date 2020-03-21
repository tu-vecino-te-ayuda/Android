package org.tuvecinoteayuda.data.profile

import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.commons.PROFILE_RESPONSE_OK
import org.tuvecinoteayuda.data.commons.REGISTER_RESPONSE_OK
import org.tuvecinoteayuda.data.commons.UPDATE_PROFILE_RESPONSE_OK
import org.tuvecinoteayuda.data.commons.models.AuthResponse
import org.tuvecinoteayuda.data.commons.models.User
import org.tuvecinoteayuda.data.profile.models.ProfileResponse
import org.tuvecinoteayuda.data.profile.models.UpdateProfileResponse
import org.tuvecinoteayuda.data.profile.repository.ProfileRepository
import org.tuvecinoteayuda.data.register.repository.RegisterRepository

class ProfileRepositoryTest {

    private val repository = mockk<ProfileRepository>()

    @Test
    fun `get profile is success`() {
        runBlockingTest {
            val registerResponse =
                Gson().fromJson<ProfileResponse>(PROFILE_RESPONSE_OK, ProfileResponse::class.java)

            coEvery {
                repository.getProfile()
            } returns ResultWrapper.Success(registerResponse)

            val result = repository.getProfile()

            assertEquals(ResultWrapper.Success(registerResponse), result)
        }
    }

    @Test
    fun `update profile is success`() {
        runBlockingTest {
            val updateResponse = Gson().fromJson<UpdateProfileResponse>(UPDATE_PROFILE_RESPONSE_OK, UpdateProfileResponse::class.java)
            val user = Gson().fromJson<ProfileResponse>(PROFILE_RESPONSE_OK, ProfileResponse::class.java).user

            user.name = "Roberto"

            coEvery {
                repository.updateProfile(any())
            } returns ResultWrapper.Success(updateResponse)

            val result = repository.updateProfile(user)

            assertEquals(ResultWrapper.Success(updateResponse), result)
        }
    }

}