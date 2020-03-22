package org.tuvecinoteayuda.data.helprequests

import org.tuvecinoteayuda.data.helprequests.models.HelpRequestResponse
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DashboardApi {

    @GET("help-requests/pending")
    fun pendingRequest(): HelpRequestResponse

    @GET("help-requests")
    fun getRequest(): HelpRequestListResponse

    @GET("help-requests/accept/{id}")
    fun acceptRequest(@Path("id") id: String): HelpRequestListResponse

}