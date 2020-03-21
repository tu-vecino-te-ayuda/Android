package org.tuvecinoteayuda.data

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @field:SerializedName("code") var code: Int,
    @field:SerializedName("code") var message: String
)