package org.tuvecinoteayuda.data.dashboard

import org.tuvecinoteayuda.data.dashboard.models.PendingRequestResponse
import org.tuvecinoteayuda.data.dashboard.models.RequestListResponse
import retrofit2.http.GET

interface DashboardApi {

    @GET("help-requests/pending")
    fun pendingRequest(): PendingRequestResponse

    @GET("help-requests")
    fun getRequest(): RequestListResponse

}