package org.tuvecinoteayuda.data

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @field:SerializedName("code") var code: Int,
    @field:SerializedName("status_code") var statusCode: Int,
    @field:SerializedName("message") var message: String,
    @field:SerializedName("status") var status: String
)