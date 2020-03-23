package org.tuvecinoteayuda.data.commons.models

import androidx.annotation.IntDef
import com.google.gson.annotations.SerializedName

data class NearByAreaTypeId(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String

) {
    override fun toString(): String {
        return name
    }

    companion object {

        @IntDef(BUILDING_ID, NEIGHBORHOOD_ID, CITY_ID, OUTSIDE_ID, NONE_NEARBY_AREA_ID)
        @Retention(AnnotationRetention.SOURCE)
        annotation class NearByAreaType

        const val BUILDING_ID = 1
        const val NEIGHBORHOOD_ID = 2
        const val CITY_ID = 3
        const val OUTSIDE_ID = 4
        const val NONE_NEARBY_AREA_ID = 99

        val BUILDING = NearByAreaTypeId(BUILDING_ID, "Solo a mis vecinos")
        val NEIGHBORHOOD = NearByAreaTypeId(NEIGHBORHOOD_ID, "Solo en mi barrio")
        val CITY = NearByAreaTypeId(CITY_ID, "Solo en mi ciudad")
        val OUTSIDE = NearByAreaTypeId(OUTSIDE_ID, "Mi ciudad y proximidades")
        val NONE = NearByAreaTypeId(NONE_NEARBY_AREA_ID, "")
    }
}