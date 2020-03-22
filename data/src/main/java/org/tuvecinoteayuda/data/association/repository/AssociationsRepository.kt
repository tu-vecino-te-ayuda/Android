package org.tuvecinoteayuda.data.association.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.tuvecinoteayuda.data.BaseRepository
import org.tuvecinoteayuda.data.CommonInterceptor
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.ServiceFactory
import org.tuvecinoteayuda.data.association.api.AssociationApi
import org.tuvecinoteayuda.data.association.models.AssociationListResponse
import org.tuvecinoteayuda.data.commons.models.MessageResponse

class AssociationsRepository(
    private var api: AssociationApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRepository() {

    suspend fun joinAssociation(id: String): ResultWrapper<MessageResponse> {
        return safeApiCall(dispatcher) { api.joinAssociation(id) }
    }

    suspend fun detachAssociation(id: String): ResultWrapper<MessageResponse> {
        return safeApiCall(dispatcher) { api.detachAssociation(id) }
    }

    suspend fun getAssociationMembers(): ResultWrapper<AssociationListResponse> {
        return safeApiCall(dispatcher) { api.getAssociationMembers() }
    }

    suspend fun getAllAssociations(): ResultWrapper<AssociationListResponse> {
        return safeApiCall(dispatcher) { api.getAllAssociations() }
    }

    companion object {
        fun newInstance(dispatcher: CoroutineDispatcher): AssociationsRepository {
            return AssociationsRepository(
                ServiceFactory.create(CommonInterceptor.newInstance()), dispatcher
            )
        }
    }
}