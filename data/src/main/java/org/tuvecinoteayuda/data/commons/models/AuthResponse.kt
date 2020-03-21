package org.tuvecinoteayuda.data.commons.models

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @field:SerializedName("user") val user: User,
    @field:SerializedName("token") val token: String
)
