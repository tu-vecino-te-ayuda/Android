package org.tuvecinoteayuda.data.dashboard.models

import com.google.gson.annotations.SerializedName

data class RequestListResponse(

	@field:SerializedName("show_assigned_additional_data")
	val showAssignedAdditionalData: Boolean,

	@field:SerializedName("data")
	val data: List<HelpRequest>,

	@field:SerializedName("show_direction")
	val showDirection: Boolean
)