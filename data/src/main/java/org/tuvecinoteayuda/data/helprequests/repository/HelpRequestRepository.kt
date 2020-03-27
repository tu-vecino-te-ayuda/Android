package org.tuvecinoteayuda.data.helprequests.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import org.tuvecinoteayuda.data.*
import org.tuvecinoteayuda.data.commons.models.MessageResponse
import org.tuvecinoteayuda.data.helprequests.api.HelpRequestApi
import org.tuvecinoteayuda.data.helprequests.models.CreateHelpRequestRequest
import org.tuvecinoteayuda.data.helprequests.models.HelpRequest
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestListResponse
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestResponse
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestTypeResponse

class HelpRequestRepository(
    private val api: HelpRequestApi,
    private val dispatcher: CoroutineDispatcher
) : BaseRepository() {

    private var helpRequestResponse: HelpRequestResponse? = null

    suspend fun getPendingRequestList(): ResultWrapper<HelpRequestResponse> {
        val result = safeApiCallAsync(dispatcher) { api.pendingRequest() }.await()

        when (result) {
            is ResultWrapper.Success -> {
                cacheHelpRequestAsync(result.value).await()
            }
        }

        return result
    }

    suspend fun getRequest(): ResultWrapper<HelpRequestListResponse> {
        return safeApiCall(dispatcher) { api.getRequest() }
    }

    suspend fun getHelpRequestTypes(): ResultWrapper<HelpRequestTypeResponse> {
        return safeApiCall(dispatcher) { api.getHelpRequestTypes() }
    }

    suspend fun getMyHelpRequests(): ResultWrapper<HelpRequestListResponse> {
        return safeApiCall(dispatcher) { api.getMyHelpRequests() }
    }

    suspend fun createHelpRequest(helpRequest: CreateHelpRequestRequest): ResultWrapper<MessageResponse> {
        return safeApiCall(dispatcher) { api.createHelpRequest(helpRequest) }
    }

    suspend fun deleteHelpRequest(id: String): ResultWrapper<MessageResponse> {
        return safeApiCall(dispatcher) { api.deleteHelpRequest(id) }
    }

    suspend fun getPendingHelpRequests(): ResultWrapper<HelpRequestResponse> {
        return safeApiCall(dispatcher) { api.getPendingHelpRequests() }
    }

    suspend fun acceptHelpRequest(id: String): ResultWrapper<HelpRequestListResponse> {
        return safeApiCall(dispatcher) { api.acceptHelpRequest(id) }
    }

    suspend fun cancelAcceptedHelpRequest(id: String): ResultWrapper<MessageResponse> {
        return safeApiCall(dispatcher) { api.cancelAcceptedHelpRequest(id) }
    }

    fun findRequestById(requestId: Int): HelpRequest? {
        return this.helpRequestResponse?.data?.firstOrNull { it.id == requestId }
    }

    private suspend fun cacheHelpRequestAsync(helpRequestResponse: HelpRequestResponse): Deferred<Unit> {
        return callAsync(dispatcher) { this.helpRequestResponse = helpRequestResponse }
    }

    companion object {

        @Volatile
        private var INSTANCE: HelpRequestRepository? = null

        fun getInstance(dispatcher: CoroutineDispatcher = Dispatchers.IO): HelpRequestRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildRepository(dispatcher).also { INSTANCE = it }
            }

        private fun buildRepository(dispatcher: CoroutineDispatcher): HelpRequestRepository {
            return HelpRequestRepository(
                ServiceFactory.create(CommonInterceptor.newInstance(TokenProvider)),
                dispatcher
            )
        }
    }
}
