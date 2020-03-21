package org.tuvecinoteayuda.data.commons.models

import com.google.gson.annotations.SerializedName

data class UserStatusId(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)