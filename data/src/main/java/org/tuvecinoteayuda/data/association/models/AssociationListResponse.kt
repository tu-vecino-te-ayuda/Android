package org.tuvecinoteayuda.data.association.models

import com.google.gson.annotations.SerializedName

data class AssociationListResponse(

	@field:SerializedName("data")
	val data: List<Association>
)