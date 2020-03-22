package org.tuvecinoteayuda.data.helprequests.models

import com.google.gson.annotations.SerializedName

data class HelpRequestListResponse(

	@field:SerializedName("show_assigned_additional_data")
	val showAssignedAdditionalData: Boolean,

	@field:SerializedName("data")
	val data: List<HelpRequest>,

	@field:SerializedName("show_direction")
	val showDirection: Boolean
)