package org.tuvecinoteayuda.data.helprequests.models

import com.google.gson.annotations.SerializedName

data class HelpRequestTypeResponse(

    @field:SerializedName("data")
    val data: List<HelpRequestType>
)