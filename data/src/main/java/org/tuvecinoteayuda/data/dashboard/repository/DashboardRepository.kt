package org.tuvecinoteayuda.data.dashboard.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.tuvecinoteayuda.data.BaseRepository
import org.tuvecinoteayuda.data.CommonInterceptor
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.ServiceFactory
import org.tuvecinoteayuda.data.dashboard.DashboardApi
import org.tuvecinoteayuda.data.dashboard.models.PendingRequestResponse
import org.tuvecinoteayuda.data.dashboard.models.RequestListResponse

class DashboardRepository(
    private val api: DashboardApi,
    private val dispatcher: CoroutineDispatcher
) : BaseRepository() {

    suspend fun getPendingRequestList(): ResultWrapper<PendingRequestResponse> {
        return safeApiCall(dispatcher) { api.pendingRequest() }
    }

    suspend fun getRequest(): ResultWrapper<RequestListResponse> {
        return safeApiCall(dispatcher) { api.getRequest() }
    }

    companion object {
        fun newInstance(dispatcher: CoroutineDispatcher = Dispatchers.IO): DashboardRepository {
            return DashboardRepository(
                ServiceFactory.create(CommonInterceptor.newInstance()),
                dispatcher
            )
        }
    }
}