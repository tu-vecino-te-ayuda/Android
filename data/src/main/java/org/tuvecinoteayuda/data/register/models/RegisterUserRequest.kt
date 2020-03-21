package org.tuvecinoteayuda.data.register.models

import androidx.annotation.IntDef
import com.google.gson.annotations.SerializedName
import org.tuvecinoteayuda.data.commons.models.UserTypeId

data class RegisterUserRequest(
    @field:SerializedName("name") var name: String,
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
    @field:SerializedName("nearby_areas_id") @NearByAreaType var nearbyAreasId: Int?,
    @field:SerializedName("activity_areas_id") @ActivityAreaType var activityAreaType: Int?
) {

    companion object {

        @IntDef(BUILDING, NEIGHBORHOOD, CITY, OUTSIDE, NONE_NEARBY_AREA)
        @Retention(AnnotationRetention.SOURCE)
        annotation class NearByAreaType

        const val BUILDING = 1
        const val NEIGHBORHOOD = 2
        const val CITY = 3
        const val OUTSIDE = 4
        const val NONE_NEARBY_AREA = 99

        @IntDef(LOCAL, STATE, COUNTRY, NONE_ACTIVITY_AREA)
        @Retention(AnnotationRetention.SOURCE)
        annotation class ActivityAreaType

        const val LOCAL = 1
        const val STATE = 2
        const val COUNTRY = 3
        const val NONE_ACTIVITY_AREA = 99
    }

}
