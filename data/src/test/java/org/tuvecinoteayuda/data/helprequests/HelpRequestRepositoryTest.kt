package org.tuvecinoteayuda.data.helprequests

import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.commons.ACCEPT_HELP_REQUESTS_RESPONSE_OK
import org.tuvecinoteayuda.data.commons.CANCEL_HELP_REQUESTS_RESPONSE_OK
import org.tuvecinoteayuda.data.commons.HELP_REQUESTS_RESPONSE_OK
import org.tuvecinoteayuda.data.commons.PENDING_REQUESTS_RESPONSE_OK
import org.tuvecinoteayuda.data.commons.models.MessageResponse
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestListResponse
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestResponse
import org.tuvecinoteayuda.data.helprequests.repository.HelpRequestRepository

class HelpRequestRepositoryTest {

    private val repository = mockk<HelpRequestRepository>(relaxed = true)

    @Test
    fun `get pending request is success`() {
        runBlockingTest {
            val loginResponse =
                Gson().fromJson<HelpRequestResponse>(
                    PENDING_REQUESTS_RESPONSE_OK,
                    HelpRequestResponse::class.java
                )

            coEvery {
                repository.getPendingRequestList()
            } returns ResultWrapper.Success(loginResponse)

            val result = repository.getPendingRequestList()
            assertEquals(ResultWrapper.Success(loginResponse), result)
        }
    }

    @Test
    fun `get help request is success`() {
        runBlockingTest {
            val requestResponse =
                Gson().fromJson<HelpRequestListResponse>(
                    HELP_REQUESTS_RESPONSE_OK,
                    HelpRequestListResponse::class.java
                )

            coEvery {
                repository.getRequest()
            } returns ResultWrapper.Success(requestResponse)

            val result = repository.getRequest()
            assertEquals(ResultWrapper.Success(requestResponse), result)
        }
    }

    @Test
    fun `accept help request is success`() {
        runBlockingTest {
            val requestResponse =
                Gson().fromJson<HelpRequestListResponse>(
                    ACCEPT_HELP_REQUESTS_RESPONSE_OK,
                    HelpRequestListResponse::class.java
                )

            coEvery {
                repository.acceptRequest(any())
            } returns ResultWrapper.Success(requestResponse)

            val result = repository.acceptRequest("")
            assertEquals(ResultWrapper.Success(requestResponse), result)
        }
    }

    @Test
    fun `cancel help request is success`() {
        runBlockingTest {
            val requestResponse =
                Gson().fromJson<MessageResponse>(
                    CANCEL_HELP_REQUESTS_RESPONSE_OK,
                    MessageResponse::class.java
                )

            coEvery {
                repository.cancelAcceptedRequest(any())
            } returns ResultWrapper.Success(requestResponse)

            val result = repository.cancelAcceptedRequest("")
            assertEquals(ResultWrapper.Success(requestResponse), result)
        }
    }
}