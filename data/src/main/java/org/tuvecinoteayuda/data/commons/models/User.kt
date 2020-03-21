package org.tuvecinoteayuda.data.commons.models

import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("associations")
    var associations: List<Any?>? = null,
    @field:SerializedName("address")
    var address: String? = null,

    @field:SerializedName("phone")
    var phone: String? = null,

    @field:SerializedName("city")
    var city: String? = null,

    @field:SerializedName("user_status_id")
    var userStatusId: UserStatusId? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("user_type_id")
    var userTypeId: UserTypeId? = null,

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("state")
    var state: String? = null,

    @field:SerializedName("nearby_areas_id")
    var nearbyAreasId: AreaTypeId? = null,

    @field:SerializedName("email")
    var email: String? = null,

    @field:SerializedName("zip_code")
    var zipCode: String? = null
)