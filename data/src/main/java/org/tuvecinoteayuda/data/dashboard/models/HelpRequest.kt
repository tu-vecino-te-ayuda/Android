package org.tuvecinoteayuda.data.dashboard.models

import com.google.gson.annotations.SerializedName
import org.tuvecinoteayuda.data.commons.models.User

data class HelpRequest(

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("assigned_user_id")
	val assignedUserId: List<User>,

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("help_request_type")
	val helpRequestType: HelpRequestType,

	@field:SerializedName("accepted_at")
	val acceptedAt: String,

	@field:SerializedName("assigned_user_count")
	val assignedUserCount: Int
)