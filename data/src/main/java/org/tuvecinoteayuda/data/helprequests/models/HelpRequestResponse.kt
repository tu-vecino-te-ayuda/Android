package org.tuvecinoteayuda.data.helprequests.models

import com.google.gson.annotations.SerializedName

data class HelpRequestResponse(

	@field:SerializedName("data")
	val data: List<HelpRequest>
)