package org.tuvecinoteayuda.data.helprequests.models

import com.google.gson.annotations.SerializedName

data class HelpRequestType(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
) {

    override fun toString(): String {
        return name
    }
}
