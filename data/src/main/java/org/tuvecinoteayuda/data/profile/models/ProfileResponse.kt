package org.tuvecinoteayuda.data.profile.models

import com.google.gson.annotations.SerializedName
import org.tuvecinoteayuda.data.commons.models.User

data class ProfileResponse(

	@field:SerializedName("data")
	val user: User
)