package org.tuvecinoteayuda.data.dashboard

import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.commons.HELP_REQUESTS_RESPONSE_OK
import org.tuvecinoteayuda.data.commons.PENDING_REQUESTS_RESPONSE_OK
import org.tuvecinoteayuda.data.dashboard.models.PendingRequestResponse
import org.tuvecinoteayuda.data.dashboard.models.RequestListResponse
import org.tuvecinoteayuda.data.dashboard.repository.DashboardRepository

class DashboardRepositoryTest {

    private val repository = mockk<DashboardRepository>(relaxed = true)

    @Test
    fun `get pending request is success`() {
        runBlockingTest {
            val loginResponse =
                Gson().fromJson<PendingRequestResponse>(
                    PENDING_REQUESTS_RESPONSE_OK,
                    PendingRequestResponse::class.java
                )

            coEvery {
                repository.getPendingRequestList()
            } returns ResultWrapper.Success(loginResponse)

            val result = repository.getPendingRequestList()
            TestCase.assertEquals(ResultWrapper.Success(loginResponse), result)
        }
    }

    @Test
    fun `get help request is success`() {
        runBlockingTest {
            val requestResponse =
                Gson().fromJson<RequestListResponse>(
                    HELP_REQUESTS_RESPONSE_OK,
                    RequestListResponse::class.java
                )

            coEvery {
                repository.getRequest()
            } returns ResultWrapper.Success(requestResponse)

            val result = repository.getRequest()
            TestCase.assertEquals(ResultWrapper.Success(requestResponse), result)
        }
    }
}