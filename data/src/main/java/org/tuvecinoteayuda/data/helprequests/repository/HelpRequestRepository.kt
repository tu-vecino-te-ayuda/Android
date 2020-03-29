package org.tuvecinoteayuda.data.helprequests.repository

import kotlinx.coroutines.*
import org.tuvecinoteayuda.data.*
import org.tuvecinoteayuda.data.commons.models.MessageResponse
import org.tuvecinoteayuda.data.helprequests.api.HelpRequestApi
import org.tuvecinoteayuda.data.helprequests.models.*

class HelpRequestRepository(
    private val api: HelpRequestApi,
    private val dispatcher: CoroutineDispatcher
) : BaseRepository() {

    private var pendingHelpRequestCache: List<HelpRequest>? = null
    private var myHelpRequestCache: List<HelpRequest>? = null

    suspend fun getHelpRequestTypes(): ResultWrapper<HelpRequestTypeResponse> {
        return safeApiCall(dispatcher) { api.getHelpRequestTypes() }
    }

    suspend fun createHelpRequest(helpRequest: CreateHelpRequestRequest): ResultWrapper<MessageResponse> {
        return safeApiCall(dispatcher) { api.createHelpRequest(helpRequest) }
    }

    suspend fun deleteHelpRequest(id: String): ResultWrapper<MessageResponse> {
        return safeApiCall(dispatcher) { api.deleteHelpRequest(id) }
    }

    suspend fun getMyHelpRequests(): ResultWrapper<HelpRequestListResponse> {
        return safeApiCall(dispatcher) { api.getMyHelpRequests() }
            .also { cacheMyHelpRequest(it) }
    }

    suspend fun getPendingHelpRequests(): ResultWrapper<HelpRequestResponse> {
        return safeApiCall(dispatcher) { api.getPendingHelpRequests() }
            .also { cachePendingHelpRequest(it) }
    }

    suspend fun acceptHelpRequest(id: String): ResultWrapper<HelpRequestListResponse> {
        return safeApiCall(dispatcher) { api.acceptHelpRequest(id) }
    }

    suspend fun cancelAcceptedHelpRequest(id: String): ResultWrapper<MessageResponse> {
        return safeApiCall(dispatcher) { api.cancelAcceptedHelpRequest(id) }
    }

    suspend fun findRequestById(requestId: String): HelpRequest? {
        return coroutineScope {
            withContext(Dispatchers.Default) {
                myHelpRequestCache?.firstOrNull { it.id == requestId }
                    ?.run { return@withContext this }
                pendingHelpRequestCache?.firstOrNull { it.id == requestId }
            }
        }
    }

    private suspend fun cachePendingHelpRequest(response: ResultWrapper<HelpRequestResponse>) {
        coroutineScope {
            launch {
                when (response) {
                    is ResultWrapper.Success -> {
                        pendingHelpRequestCache = response.value.data
                    }
                }
            }
        }
    }

    private suspend fun cacheMyHelpRequest(response: ResultWrapper<HelpRequestListResponse>) {
        coroutineScope {
            launch {
                when (response) {
                    is ResultWrapper.Success -> {
                        myHelpRequestCache = response.value.data
                    }
                }
            }
        }
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
