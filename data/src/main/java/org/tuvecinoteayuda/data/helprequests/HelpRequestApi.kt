package org.tuvecinoteayuda.data.helprequests

import org.tuvecinoteayuda.data.commons.models.MessageResponse
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestListResponse
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface HelpRequestApi {

    @GET("help-requests/pending")
    fun pendingRequest(): HelpRequestResponse

    @GET("help-requests")
    fun getRequest(): HelpRequestListResponse

    @GET("help-requests/accept/{id}")
    fun acceptRequest(@Path("id") id: String): HelpRequestListResponse

    @DELETE("help-requests/revert/{id}")
    fun cancelAcceptedRequest(@Path("id") id: String): MessageResponse

    @DELETE("help-requests/{id}")
    fun cancelMyRequest(@Path("id") id: String): MessageResponse

}