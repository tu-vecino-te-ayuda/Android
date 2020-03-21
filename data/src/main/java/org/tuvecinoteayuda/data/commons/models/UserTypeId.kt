package org.tuvecinoteayuda.data.commons.models

import androidx.annotation.IntDef
import com.google.gson.annotations.SerializedName

data class UserTypeId(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
){
	companion object {

		@IntDef(SOLICITANTE, VOLUNTARIO, ENTIDAD)
		@Retention(AnnotationRetention.SOURCE)
		annotation class UserType

		const val SOLICITANTE = 1
		const val VOLUNTARIO = 2
		const val ENTIDAD = 3
	}
}