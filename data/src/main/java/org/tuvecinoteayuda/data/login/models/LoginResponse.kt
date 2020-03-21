package org.tuvecinoteayuda.data.login.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("user") val user: User,
    @field:SerializedName("token") val token: String
)