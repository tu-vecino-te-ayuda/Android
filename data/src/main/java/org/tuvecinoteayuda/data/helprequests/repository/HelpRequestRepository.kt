package org.tuvecinoteayuda.data.helprequests.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.tuvecinoteayuda.data.*
import org.tuvecinoteayuda.data.commons.models.MessageResponse
import org.tuvecinoteayuda.data.helprequests.api.HelpRequestApi
import org.tuvecinoteayuda.data.helprequests.models.CreateHelpRequestRequest
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestListResponse
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestResponse
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestTypeResponse

class HelpRequestRepository(
    private val api: HelpRequestApi,
    private val dispatcher: CoroutineDispatcher
) : BaseRepository() {

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

    companion object {
        fun newInstance(dispatcher: CoroutineDispatcher = Dispatchers.IO): HelpRequestRepository {
            return HelpRequestRepository(
                ServiceFactory.create(CommonInterceptor.newInstance(TokenProvider)),
                dispatcher
            )
        }
    }
}
