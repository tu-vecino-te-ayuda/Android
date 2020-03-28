package org.tuvecinoteayuda.data.helprequests.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
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

    private var helpRequestResponseCache: List<HelpRequest>? = null

    private suspend fun cacheHelpRequest(helpRequestList:  List<HelpRequest>) {
        coroutineScope {
            helpRequestResponseCache = helpRequestList
        }
    }

    suspend fun getPendingHelpRequestListAndCache(): ResultWrapper<HelpRequestResponse> {
        val result = getPendingHelpRequest()
        when (result) {
            is ResultWrapper.Success -> {
                cacheHelpRequest(result.value.data)
            }
        }

        return result
    }

    suspend fun getPendingHelpRequest(): ResultWrapper<HelpRequestResponse> {
        return safeApiCall(dispatcher) { api.getPendingHelpRequests() }
    }

    suspend fun getMyRequestAndCache(): ResultWrapper<HelpRequestListResponse> {
        val result = getMyHelpRequests()
        when (result) {
            is ResultWrapper.Success -> {
                cacheHelpRequest(result.value.data)
            }
        }

        return result
    }

    suspend fun getMyHelpRequests(): ResultWrapper<HelpRequestListResponse> {
        return safeApiCall(dispatcher) { api.getMyHelpRequests() }
    }

    suspend fun getHelpRequestTypes(): ResultWrapper<HelpRequestTypeResponse> {
        return safeApiCall(dispatcher) { api.getHelpRequestTypes() }
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

    fun findRequestById(requestId: String): HelpRequest? {
        return this.helpRequestResponseCache?.firstOrNull { it.id == requestId }
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
