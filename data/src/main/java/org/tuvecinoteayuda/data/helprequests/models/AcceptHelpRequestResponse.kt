package org.tuvecinoteayuda.data.helprequests.models

import com.google.gson.annotations.SerializedName

data class AcceptHelpRequestResponse(

    @field:SerializedName("data")
    val data: HelpRequest
)
