package org.tuvecinoteayuda.data.register.models

import androidx.annotation.IntDef
import com.google.gson.annotations.SerializedName
import org.tuvecinoteayuda.data.commons.models.ActivityAreaTypeId
import org.tuvecinoteayuda.data.commons.models.NearByAreaTypeId
import org.tuvecinoteayuda.data.commons.models.UserTypeId

data class RegisterUserRequest(
    @field:SerializedName("name") var name: String,
    @field:SerializedName("email") var email: String,
    @field:SerializedName("phone") var phone: String,
    @field:SerializedName("password") var password: String,
    @field:SerializedName("password_confirmation") var passwordConfirmation: String,
    @field:SerializedName("address") var address: String,
    @field:SerializedName("city") var city: String,
    @field:SerializedName("state") var state: String,
    @field:SerializedName("zip_code") var zipCode: String,
    @field:SerializedName("corporate_name") var corporateName: String?,
    @field:SerializedName("cif") var cif: String?,
    @field:SerializedName("user_type_id") @UserTypeId.Companion.UserType var userTypeId: Int,
    @field:SerializedName("nearby_areas_id") @NearByAreaTypeId.Companion.NearByAreaType var nearbyAreasId: Int?,
    @field:SerializedName("activity_areas_id") @ActivityAreaTypeId.Companion.ActivityAreaType var activityAreaType: Int?
) {
}
