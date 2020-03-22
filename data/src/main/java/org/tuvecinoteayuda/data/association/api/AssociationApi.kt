package org.tuvecinoteayuda.data.association.api

import org.tuvecinoteayuda.data.association.models.AssociationListResponse
import org.tuvecinoteayuda.data.commons.models.MessageResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AssociationApi {

    @POST("user/association/join/{id}")
    suspend fun joinAssociation(@Path("id") id: String): MessageResponse

    @POST("user/association/detach/{id}")
    suspend fun detachAssociation(@Path("id") id: String): MessageResponse

    @GET("user/associates")
    suspend fun getAssociationMembers(): AssociationListResponse

    @GET("user/associates")
    suspend fun getAllAssociations(): AssociationListResponse
}