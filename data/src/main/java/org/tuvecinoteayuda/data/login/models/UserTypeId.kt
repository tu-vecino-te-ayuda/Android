package org.tuvecinoteayuda.data.login.models

import com.google.gson.annotations.SerializedName

data class UserTypeId(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)