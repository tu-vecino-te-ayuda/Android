package org.tuvecinoteayuda.data.helprequests.models

import com.google.gson.annotations.SerializedName

data class CreateHelpRequestRequest(

    @field:SerializedName("help_request_type")
    val helpRequestType: HelpRequestType,

	@field:SerializedName("message")
    val message: String
)
