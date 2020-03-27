package org.tuvecinoteayuda.data.helprequests.api

import org.tuvecinoteayuda.data.commons.models.MessageResponse
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestListResponse
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestResponse
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestTypeResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface HelpRequestApi {

    @GET("help-requests/pending")
    suspend fun pendingRequest(): HelpRequestResponse

    @GET("help-requests")
    suspend fun getRequest(): HelpRequestListResponse

    @GET("help-request-types")
    suspend fun getHelpRequestTypes(): HelpRequestTypeResponse

    @GET("help-requests/accept/{id}")
    suspend fun acceptRequest(@Path("id") id: String): HelpRequestListResponse

    @DELETE("help-requests/revert/{id}")
    suspend fun cancelAcceptedRequest(@Path("id") id: String): MessageResponse

    @DELETE("help-requests/{id}")
    suspend fun cancelMyRequest(@Path("id") id: String): MessageResponse

}
