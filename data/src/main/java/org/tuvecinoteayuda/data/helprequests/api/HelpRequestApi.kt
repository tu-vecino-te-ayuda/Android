package org.tuvecinoteayuda.data.helprequests.api

import org.tuvecinoteayuda.data.commons.models.MessageResponse
import org.tuvecinoteayuda.data.helprequests.models.*
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

    @POST("help-requests/accept/{id}")
    suspend fun acceptHelpRequest(@Path("id") id: String): AcceptHelpRequestResponse

    @DELETE("help-requests/revert/{id}")
    suspend fun cancelAcceptedHelpRequest(@Path("id") id: String): MessageResponse

}
