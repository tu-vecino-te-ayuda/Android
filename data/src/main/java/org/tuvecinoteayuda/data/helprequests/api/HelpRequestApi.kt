package org.tuvecinoteayuda.data.helprequests.api

import org.tuvecinoteayuda.data.commons.models.MessageResponse
import org.tuvecinoteayuda.data.helprequests.models.CreateHelpRequestRequest
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestListResponse
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestResponse
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestTypeResponse
import retrofit2.http.*

interface HelpRequestApi {

    @GET("help-request-types")
    suspend fun getHelpRequestTypes(): HelpRequestTypeResponse

    @GET("help-requests")
    suspend fun getMyHelpRequests(): HelpRequestListResponse

    @POST("help-requests")
    suspend fun createHelpRequest(@Body helpRequest: CreateHelpRequestRequest): MessageResponse

    @DELETE("help-requests/{id}")
    suspend fun deleteHelpRequest(@Path("id") id: String): MessageResponse

    @GET("help-requests/pending")
    suspend fun getPendingHelpRequests(): HelpRequestResponse

    @GET("help-requests/accept/{id}")
    suspend fun acceptHelpRequest(@Path("id") id: String): HelpRequestListResponse

    @DELETE("help-requests/revert/{id}")
    suspend fun cancelAcceptedHelpRequest(@Path("id") id: String): MessageResponse

}
