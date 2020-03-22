package org.tuvecinoteayuda.data.dashboard.models

import com.google.gson.annotations.SerializedName

data class PendingRequestResponse(

	@field:SerializedName("data")
	val data: List<HelpRequest>
)