package org.tuvecinoteayuda.data.association.models

import com.google.gson.annotations.SerializedName
import org.tuvecinoteayuda.data.commons.models.ActivityAreaTypeId
import org.tuvecinoteayuda.data.commons.models.NearByAreaTypeId
import org.tuvecinoteayuda.data.commons.models.UserTypeId

data class Association(

	@field:SerializedName("cif")
	val cif: String,

	@field:SerializedName("activity_areas_id")
	val activityAreasId: ActivityAreaTypeId,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("corporate_name")
	val corporateName: String,

	@field:SerializedName("user_type_id")
	val userTypeId: UserTypeId,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("state")
	val state: String,

	@field:SerializedName("zip_code")
	val zipCode: String
)