package org.tuvecinoteayuda.data.commons.models

import com.google.gson.annotations.SerializedName
import org.tuvecinoteayuda.data.association.models.Association

data class User(
    @field:SerializedName("associations")
    var associations: List<Association>,

    @field:SerializedName("address")
    var address: String,

    @field:SerializedName("phone")
    var phone: String,

    @field:SerializedName("city")
    var city: String,

    @field:SerializedName("user_status_id")
    var userStatusId: UserTypeId,

    @field:SerializedName("name")
    var name: String,

    @field:SerializedName("user_type_id")
    var userTypeId: UserTypeId,

    @field:SerializedName("id")
    var id: Int,

    @field:SerializedName("state")
    var state: String,

    @field:SerializedName("nearby_areas_id")
    var nearbyAreasId: String,

    @field:SerializedName("email")
    var email: String,

    @field:SerializedName("zip_code")
    var zipCode: String? = null
)