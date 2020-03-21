package org.tuvecinoteayuda.data.login.models

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @field:SerializedName("user") val user: String,
    @field:SerializedName("password") val password: String
)