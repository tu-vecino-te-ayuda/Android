package org.tuvecinoteayuda.data.commons.models

import androidx.annotation.IntDef
import com.google.gson.annotations.SerializedName

data class UserTypeId(
    @field:SerializedName("id")
    @UserType val id: Int,

    @field:SerializedName("name")
    val name: String
) {
    companion object {

        @IntDef(SOLICITANTE_ID, VOLUNTARIO_ID, ENTIDAD_ID)
        @Retention(AnnotationRetention.SOURCE)
        annotation class UserType

        const val SOLICITANTE_ID = 1
        const val VOLUNTARIO_ID = 2
        const val ENTIDAD_ID = 3

        val APPLICANT = UserTypeId(SOLICITANTE_ID, "Solicitante")
        val VOLUNTARY = UserTypeId(SOLICITANTE_ID, "Voluntario")
        val ENTITY = UserTypeId(ENTIDAD_ID, "Entidad")
    }
}