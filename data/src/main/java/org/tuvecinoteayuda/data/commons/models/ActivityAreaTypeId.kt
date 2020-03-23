package org.tuvecinoteayuda.data.commons.models

import androidx.annotation.IntDef
import com.google.gson.annotations.SerializedName

data class ActivityAreaTypeId(

	@field:SerializedName("id")
	@ActivityAreaType val id: Int? = null,

	@field:SerializedName("name")
	val name: String? = null

){

	companion object {

		@IntDef(LOCAL_ID, STATE_ID, COUNTRY_ID, NONE_ACTIVITY_ARE_IDA)
		@Retention(AnnotationRetention.SOURCE)
		annotation class ActivityAreaType

		const val LOCAL_ID = 1
		const val STATE_ID = 2
		const val COUNTRY_ID = 3
		const val NONE_ACTIVITY_ARE_IDA = 99

		val LOCAL = ActivityAreaTypeId(LOCAL_ID, "Local")
		val STATE = ActivityAreaTypeId(STATE_ID, "Provincial")
		val COUNTRY = ActivityAreaTypeId(COUNTRY_ID, "Nacional")
		val NONE = ActivityAreaTypeId(NONE_ACTIVITY_ARE_IDA, "")

	}
}