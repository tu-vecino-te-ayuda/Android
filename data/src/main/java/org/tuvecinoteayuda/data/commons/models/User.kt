package org.tuvecinoteayuda.data.commons.models

import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("associations")
    val associations: List<Any?>? = null,
    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("user_status_id")
    val userStatusId: UserStatusId? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("user_type_id")
    val userTypeId: UserTypeId? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("state")
    val state: String? = null,

    @field:SerializedName("nearby_areas_id")
    val nearbyAreasId: Int? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("zip_code")
    val zipCode: String? = null
)