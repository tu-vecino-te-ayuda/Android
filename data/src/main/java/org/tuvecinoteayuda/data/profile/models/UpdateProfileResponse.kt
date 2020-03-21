package org.tuvecinoteayuda.data.profile.models

import com.google.gson.annotations.SerializedName
import org.tuvecinoteayuda.data.commons.models.User

data class UpdateProfileResponse(

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("user")
    val user: User? = null
)