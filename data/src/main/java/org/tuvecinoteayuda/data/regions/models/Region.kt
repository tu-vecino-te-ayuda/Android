package org.tuvecinoteayuda.data.regions.models

import com.google.gson.annotations.SerializedName

data class Region(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("nm") val name: String,
    @field:SerializedName("cities") val cities: List<City>
)