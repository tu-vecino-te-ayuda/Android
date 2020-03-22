package org.tuvecinoteayuda.data.commons.models

import com.google.gson.annotations.SerializedName

data class MessageResponse(

    @field:SerializedName("status_code")
    val statusCode: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)